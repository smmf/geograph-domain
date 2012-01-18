package org.cloudtm.framework;

public interface TransactionalCommand<T> {
    public T doIt();
}
