package org.cloudtm.framework.fenix;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;

import it.algo.geograph.domain.*;
import org.cloudtm.framework.TransactionalCommand;
import org.cloudtm.framework.TxManager;

public class FFTxManager extends TxManager {

  public FFTxManager() {
    Config config = new Config() {

      {
        domainModelPath = "/geograph.dml";
        // repositoryType = pt.ist.fenixframework.Config.RepositoryType.HBASE;
        dbAlias = "//localhost:3306/geographFenix";
        dbUsername = "geograph";
        dbPassword = "geograph";
        // updateRepositoryStructureIfNeeded = true;
        rootClass = Root.class;
      }
    };
    Config configBDB = new Config() {

      {
        domainModelPath = "/geograph.dml";
        dbAlias = "/tmp/geograph-bdb";
        dbUsername = "";
        dbPassword = "";
        repositoryType = pt.ist.fenixframework.Config.RepositoryType.BERKELEYDB;
        updateRepositoryStructureIfNeeded = true;
        rootClass = Root.class;
      }
    };
    Config configInfinispanNoFile = new Config() {

      {
        domainModelPath = "src/common/dml/geograph.dml";
        dbAlias = "config/infinispanNoFile.xml";
        repositoryType = pt.ist.fenixframework.Config.RepositoryType.INFINISPAN;
        rootClass = Root.class;
      }
    };
    Config configInfinispanFile = new Config() {

      {
        domainModelPath = "/geograph.dml";
        dbAlias = "infinispanFile.xml";
        repositoryType = pt.ist.fenixframework.Config.RepositoryType.INFINISPAN;
        rootClass = Root.class;
      }
    };
    Config configNoRepository = new Config() {

      {
        domainModelPath = "/geograph.dml";
        dbAlias = "";
        repositoryType = pt.ist.fenixframework.Config.RepositoryType.NO_WRITE_REPOSITORY;
        rootClass = Root.class;
      }
    };

    FenixFramework.initialize(configInfinispanNoFile);
  }

  public FFTxManager(Config config) {
    try {
      FenixFramework.initialize(config);
    } catch(Error ex) {
      System.out.println("Fenix Framework initialization error: " + ex.getMessage());
    }
  }

  public <T> T getRoot() {
    return (T) FenixFramework.getRoot();
  }

  public <T> T getDomainObject(Class<T> clazz, Object oid) {
    return (T) pt.ist.fenixframework.pstm.AbstractDomainObject.fromOID((Long) oid);
  }

  public <T> T withTransaction(final TransactionalCommand<T> command) {
    T result = null;
    boolean tryReadOnly = true;
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
}
