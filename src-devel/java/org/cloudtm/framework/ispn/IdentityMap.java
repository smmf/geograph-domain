package org.cloudtm.framework.ispn;

public interface IdentityMap {

    public AbstractDomainObject cache(AbstractDomainObject obj);
    public AbstractDomainObject lookup(String key);
}
