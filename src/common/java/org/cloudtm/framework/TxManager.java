package org.cloudtm.framework;

public abstract class TxManager {

    public void save(Object obj) {
        // do nothing by default
    }

    public void setRootIdIfNeeded(Object rootObject) {
        // do nothing by default
    }

    public abstract <T> T getRoot();
    public abstract <T> T getDomainObject(Class<T> clazz, Object oid);
    public abstract <T> T withTransaction(TransactionalCommand<T> command);
}
