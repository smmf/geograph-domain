package org.cloudtm.framework.ogm;

//import org.hibernate.transaction.JBossTSStandaloneTransactionManagerLookup;
import org.infinispan.transaction.lookup.JBossStandaloneJTAManagerLookup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import org.cloudtm.framework.TransactionalCommand;
import org.cloudtm.framework.TxManager;
//import it.algo.geograph.TxSystem;

import it.algo.geograph.domain.*;
import org.cloudtm.framework.TransactionalCommand;
import org.cloudtm.framework.TxManager;

public class HibOgmTxManager extends TxManager {
    // static stuff used for controlling the transactional system and persistence
    private static final TransactionManager transactionManager = null;
         //new JBossStandaloneJTAManagerLookup().getTransactionManager();
        //new JBossTSStandaloneTransactionManagerLookup().getTransactionManager(null);

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("geograph.persistence.unit");

    private static final ThreadLocal<EntityManager> currentEntityManager = new ThreadLocal<EntityManager>();

    private static EntityManager getEntityManager() {
        return currentEntityManager.get();
    }

    public void save(Object obj) {
        getEntityManager().persist(obj);
    }

    // this method should only be invoked within a transaction
    public <T> T getRoot() {
        Root root = getDomainObject(Root.class, 1L);
        if (root == null) { // not found
            root = new Root();
            save(root);
        }
        // System.out.println("DONE");
        return (T)root;
    }

    // this method should only be invoked within a transaction
    public <T> T getDomainObject(Class<T> clazz, Object oid) {
        return getEntityManager().find(clazz, oid);
    }

    public <T> T withTransaction(final TransactionalCommand<T> command) {
        T result = null;
        boolean txFinished = false;
        while (!txFinished) {
            EntityManager em = null;
            try {
                transactionManager.begin();
                em = emf.createEntityManager();
                currentEntityManager.set(em);

                // do some work
                result = command.doIt();
            
                em.flush();
                em.close();
                transactionManager.commit();
                txFinished = true;
                return result;
            } catch (org.infinispan.CacheException ce) {
                // System.out.println("CONFLICT!");
                // tx will be rolled back and retried
            } catch (Exception e) { // any other exception gets out
                System.out.println("================================================================================");
                System.out.println("class: " + e.getClass());
                System.out.println("cause: " + e.getCause());
                e.printStackTrace();
                System.out.println("================================================================================");
                throw new RuntimeException(e);
            } finally {
                if (!txFinished) {
                    try { transactionManager.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
                }
                currentEntityManager.set(null);
            }
        }
        // never reached
        throw new RuntimeException("code never reached");
    }


}