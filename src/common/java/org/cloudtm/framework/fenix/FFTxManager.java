package org.cloudtm.framework.fenix;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;

import org.cloudtm.framework.TransactionalCommand;
import org.cloudtm.framework.TxManager;

public class FFTxManager extends TxManager {

  public FFTxManager() {}

  @Override
  public void configure(Config config) {
    try {
      FenixFramework.initialize(config);
    } catch(Error ex) {
      System.out.println("Fenix Framework initialization error: " + ex.getMessage());
    }
  }

  
  @Override
  public void stop() {}

  @Override
  public <T> T getRoot() {
    return (T) FenixFramework.getRoot();
  }

  @Override
  public <T> T getDomainObject(Class<T> clazz, Object oid) {
    return (T) pt.ist.fenixframework.pstm.AbstractDomainObject.fromOID((Long) oid);
  }

  @Override
  public <T> T withTransaction(final TransactionalCommand<T> command, boolean readonly) {
    T result = null;
    boolean tryReadOnly = readonly;
    
    while (true) {
      Transaction.begin(tryReadOnly);
      boolean finished = false;
      try {
        result = command.doIt();
        Transaction.commit();
        finished = true;
        return result;
      } catch (jvstm.CommitException ce) {
        Transaction.abort();
        finished = true;
      } catch (jvstm.WriteOnReadException wore) {
        System.out.println("jvstm.WriteOnReadException");
        Transaction.abort();
        finished = true;
        tryReadOnly = false;
      } catch (pt.ist.fenixframework.pstm.AbstractDomainObject.UnableToDetermineIdException unableToDetermineIdException) {
        System.out.println("Restaring TX: unable to determine id. Cause: " + unableToDetermineIdException.getCause());
        System.out.println(unableToDetermineIdException.toString());
        Transaction.abort();
        finished = true;
      } finally {
        if (!finished) {
          Transaction.abort();
        }
      }
    }

  }

  @Override
  public <T> T withTransaction(final TransactionalCommand<T> command) {
    return withTransaction(command, true);
  }
}
