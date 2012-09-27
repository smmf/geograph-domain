package org.cloudtm.framework;

import pt.ist.fenixframework.Config;

public abstract class TxManager {
    public void save(Object obj) {
        // do nothing by default
    }

    public void setRootIdIfNeeded(Object rootObject) {
        // do nothing by default
    }

    public abstract void configure(Config config);
    
    public abstract <T> T getRoot();
    public abstract <T> T getDomainObject(Class<T> clazz, Object oid);
    public abstract <T> T withTransaction(TransactionalCommand<T> command);
    public abstract <T> T withTransaction(TransactionalCommand<T> command, boolean readonly);
    // When stopping the web container we need to stop JGroups.  This is here for convenience, but
    // we want this to go to a handler when unloading or stopping the web application
    public abstract void stop();
}
