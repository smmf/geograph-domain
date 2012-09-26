package org.cloudtm.framework.ispn;

import it.algo.geograph.domain.Root;
import org.apache.log4j.Logger;
import org.cloudtm.framework.TransactionalCommand;
import org.cloudtm.framework.TxManager;
import org.infinispan.Cache;
import org.infinispan.CacheException;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import pt.ist.fenixframework.Config;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.TransactionManager;
import java.io.IOException;
import java.util.Random;


public class IspnTxManager extends TxManager {
   /*
   * Ensures the unique identity of domain objects.  Before fetching a domain object from
   * Infinispan's domain cache, a lookup is performed on the identity map.  After creating a new
   * domain object instance, or after loading a domain object into main memory, the object is
   * cached.
   */
   private static final IdentityMap idMap;
   private static final ThreadLocal<IdentityMap> perTxIdMap;

   //Pedro -- added log
   private static final Logger log = Logger.getLogger(IspnTxManager.class);

   // true => use a global identity map;
   // false => use a per-transaction identity map
   private static final boolean USE_SHARED_IDENTITY_MAP = false;

   static {
      if (USE_SHARED_IDENTITY_MAP) {
         idMap = new SharedIdentityMap();
         perTxIdMap = null;
      } else {
         idMap = null;
         perTxIdMap = new ThreadLocal<IdentityMap>();
      }
   }

   public static IdentityMap getIdentityMap() {

      if (USE_SHARED_IDENTITY_MAP) {
         return idMap;
      } else {
         return perTxIdMap.get();
      }
   }
    
    private static final Object CONFIGURE_LOCK = new Object();

   /* The domain cache store the following kinds of entries:
   *
   * - <oid, object>: a domain object associated with the given oid (all domain entities have an
       entry of this type)
   *
   * - <oid1:slotname, oid2>: used in relations to 1 entity-type, this entry maps the object's
   *   slotname to the oid of the class it points to (oid2).  The object pointed to can be fetched
   *   by looking up the oid2 in the previous-style entry.
   *
   * - <oid:slotname, object>: used for value types, this entry maps the object's slotname to the
   *   value-type value of the slot.
   *
   * Entries may contain the NULL_OBJECT to represent 'null'.  The NULL_OBJECT may be present only
   * in entries for non-primitive types, regardless of being value-types or entity-types.
   *
   * Collections are supported using B+Trees.  A relation between X--->*Y, stores a B+Tree<Y> in
   * X.  Thus, collections are internally represented by a relation to 1, with X---->1 B+Tree<Y>.
   * In turn the B+Tree contains all the Ys in the relation.
   *
   */
   private static final String DOMAIN_CACHE_NAME = "DomainCache";
   private static Cache<String, Object> domainCache;

   private static TransactionManager transactionManager;

  @Override
  public void configure(Config config) {
    synchronized (CONFIGURE_LOCK) {
      if (domainCache != null) {
        log.info("Not configure DML Infinispan cache. It is already created");
        return;
      }
            
      String configurationFileLocation = config.getDbAlias();
      log.info("Configure DML Infinispan cache. Config file is " + configurationFileLocation);
      CacheContainer cc = null;
      try {
        cc = new DefaultCacheManager(configurationFileLocation);
      } catch (IOException ioe) {
        log.fatal("Error creating cache manger with configuration file: " + configurationFileLocation + ": " + ioe);
        System.exit(-1);
      }
      domainCache = cc.getCache(DOMAIN_CACHE_NAME);
      transactionManager = domainCache.getAdvancedCache().getTransactionManager();
    }
  }

   private static final Random rand = new Random();


  public static void cachePut(String key, Object value) {
    try {
      if(log.isDebugEnabled())
        log.debug("PUT " + key + " in transaction " + transactionManager.getTransaction());
    } catch (Exception e) {
      //
    }
    domainCache.put(key, (value != null) ? value : AbstractDomainObject.NULL_OBJECT);
   }

   public static <T> T cacheGet(String key) {
    try {
          if(log.isDebugEnabled())
            log.debug("GET " + key + " in transaction " + transactionManager.getTransaction());
         } catch (Exception e) {
            //
         }
      Object obj = domainCache.get(key);
      return (T)(obj instanceof AbstractDomainObject.NullClass ? null : obj);
   }

   @Override
   public void save(Object obj) {
      staticSave(obj);
   }

   public static void staticSave(Object obj) {
      AbstractDomainObject newObj = (AbstractDomainObject)obj;
      Object toConfirm = getIdentityMap().cache(newObj);
      if (toConfirm != newObj) {
         log.fatal("Another object was already cached with the same key, which violates the idea that the new obj should have a unique oid. Cannot continue.");
         System.exit(1);
      }
      domainCache.put(newObj.getOid(), newObj);
   }

   @Override
   public void setRootIdIfNeeded(Object rootObject) {
      ((AbstractDomainObject)rootObject).set$Oid(ROOT_OBJECT);
   }

   // this method should only be invoked within a transaction
   public static final String ROOT_OBJECT = "ROOT_OBJECT";
  
   @Override
   public <T> T getRoot() {
      AbstractDomainObject root = getDomainObject(Root.class, ROOT_OBJECT);
      if (root == null) { // not found
         root = Root.createNewRoot();
         save(root);
      }
      return (T)root;
   }

   // this method should only be invoked within a transaction
   @Override
   public <T> T getDomainObject(Class<T> clazz, Object oid) {
      return (T)AbstractDomainObject.fromOid((String)oid);
   }

   @Override
   public <T> T withTransaction(final TransactionalCommand<T> command) {

      if (!USE_SHARED_IDENTITY_MAP) {

         return withTransactionWithLocalIdentityMap(command);
      } else {
         T result = null;
         boolean txFinished = false;
         while (!txFinished) {
            try {
                boolean inTopLevelTransaction = false;
                // the purpose of this test is to enable reuse of the existing transaction if any
                if (transactionManager.getTransaction() == null) {
                    transactionManager.begin();
                    inTopLevelTransaction = true;
                }
               if(log.isDebugEnabled())
                if(domainCache.getAdvancedCache() !=null  && domainCache.getAdvancedCache().getRpcManager()!= null && domainCache.getAdvancedCache().getRpcManager().getTransport()!=null)
                   log.debug("cache name: " + domainCache.getName() + "\nCluster members: " + domainCache.getAdvancedCache().getRpcManager().getTransport().getMembers() + "\nCache size: " + domainCache.size());

               // do some work
               result = command.doIt();

               if (inTopLevelTransaction) {
                   transactionManager.commit();
               }
               txFinished = true;
               return result;
            } catch (CacheException ce) {
               //If the execution fails
               logExceptionAndRetry(ce);
            } catch(RollbackException re) {
               //If the transaction was marked for rollback only, the transaction is rolled back and this exception is thrown.
               logExceptionAndRetry(re);
            } catch(HeuristicMixedException hme) {
               //If a heuristic decision was made and some some parts of the transaction have been committed while other parts have been rolled back.
               //Pedro -- most of the time, happens when some nodes fails...
               logExceptionAndRetry(hme);
            } catch(HeuristicRollbackException hre) {
               //If a heuristic decision to roll back the transaction was made
               logExceptionAndRetry(hre);
            } catch (Exception e) { // any other exception 	 out
               log_error(e, null);
               throw new RuntimeException(e);
            } finally {
               if (!txFinished) {
                  try {
                     transactionManager.rollback();
                  } catch(IllegalStateException ise) {
                     // If the transaction is in a state where it cannot be rolled back.
                     // Pedro -- happen when the commit fails. When commit fails, it invokes the rollback().
                     //          so rollback() will be invoked again, but the transaction no longer exists
                     // Pedro -- just ignore it
                  } catch (Exception ex) {
                     log_error(ex, null);
                  }
               }
               // currentEntityManager.set(null);
            }
            waitingBeforeRetry();
            if(log.isDebugEnabled())
                log.warn("retying transaction: " + command);
         }
         // never reached
         throw new RuntimeException("code never reached");
      }
   }

   @Override
   public <T> T withTransaction(final TransactionalCommand<T> command, boolean readonly) {
     return withTransaction(command);
   }

   private <T> T withTransactionWithLocalIdentityMap(final TransactionalCommand<T> command) {
      T result = null;
      boolean txFinished = false;
      while (!txFinished) {
         IdentityMap localIdMap = null;
         try {
             boolean inTopLevelTransaction = false;
             // the purpose of this test is to enable reuse of the existing transaction if any
             if (transactionManager.getTransaction() == null) {
                 transactionManager.begin();
                 inTopLevelTransaction = true;
             }

            localIdMap = new LocalIdentityMap();
            perTxIdMap.set(localIdMap);
            if(log.isDebugEnabled()){
               log.debug("--cache name: " + domainCache.getName());
               if(domainCache.getAdvancedCache() !=null  && domainCache.getAdvancedCache().getRpcManager()!= null && domainCache.getAdvancedCache().getRpcManager().getTransport()!=null)
               log.debug("\nCluster members: " + domainCache.getAdvancedCache().getRpcManager().getTransport().getMembers() + "\nCache size: " + domainCache.size());
            }

            // do some work
            result = command.doIt();
            if (inTopLevelTransaction) {
                transactionManager.commit();
            }
            txFinished = true;
            return result;
         } catch (CacheException ce) {
            //If the execution fails
            logExceptionAndRetry(ce);
         } catch(RollbackException re) {
            //If the transaction was marked for rollback only, the transaction is rolled back and this exception is thrown.
            logExceptionAndRetry(re);
         } catch(HeuristicMixedException hme) {
            //If a heuristic decision was made and some some parts of the transaction have been committed while other parts have been rolled back.
            //Pedro -- most of the time, happens when some nodes fails...
            logExceptionAndRetry(hme);
         } catch(HeuristicRollbackException hre) {
            //If a heuristic decision to roll back the transaction was made
            logExceptionAndRetry(hre);
         } catch (Exception e) { // any other exception 	 out

            throw new RuntimeException(e);
         } finally {
            if (!txFinished) {
               try {
                  transactionManager.rollback();
               } catch(IllegalStateException ise) {
                  // If the transaction is in a state where it cannot be rolled back.
                  // Pedro -- happen when the commit fails. When commit fails, it invokes the rollback().
                  //          so rollback() will be invoked again, but the transaction no longer exists
                  // Pedro -- just ignore it
               } catch (Exception ex) {
                 log_error(ex, null);


               }
            }
            perTxIdMap.set(null);
         }
         waitingBeforeRetry();
         log.warn("retying transaction: " + command);
      }
      // never reached
      throw new RuntimeException("code never reached");
   }

   @Override
   public void stop() {
      domainCache.stop();
   }

   private void waitingBeforeRetry() {
      try {
         Thread.sleep(rand.nextInt(10000));
      } catch(InterruptedException ie) {
         //do nothing
      }
   }

   private void logExceptionAndRetry(Exception e) {
      log.info("Exception catch in transaction: " + e.getLocalizedMessage());
      log.debug("Exception catch in transaction:", e);
   }


  private void log_error(Throwable ex, String message){
    if(log.isInfoEnabled()){
      if(message!=null)
        log.error(message);
      log.error(ex.toString());
      Throwable cause = ex;
      //inspect all the causes
      log.error("===========================================\n");
      while(cause != null){
        log.error(cause.getMessage());
        cause = cause.getCause();
      }
      log.error("===========================================\n");
    }

  }


   //Test code

   public IspnTxManager(){
       super();
       testLogging();
       log_error(new Throwable("test error"), "ops");
      }

   private static void testLogging(){

        // System.err.println(log.getLevel());


         log.fatal("===========================================");

         //if(log.isFatalEnabled())
             log.fatal("FATAL");

         //if(log.isErrorEnabled())
             log.error("ERROR");

         //if(log.isWarnEnabled())
             log.warn("WARN");

         if(log.isInfoEnabled())
              log.info("INFO");

         if(log.isDebugEnabled())
              log.debug("DEBUG");

         if(log.isTraceEnabled())
             log.trace("TRACE");

         log.fatal("===========================================");

   }

}
