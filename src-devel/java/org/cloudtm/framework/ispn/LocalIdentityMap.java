package org.cloudtm.framework.ispn;

import java.util.Map;
import java.util.HashMap;

public class LocalIdentityMap implements IdentityMap {

    private HashMap<String,AbstractDomainObject> cache;

    public LocalIdentityMap() {
	this.cache = new HashMap<String,AbstractDomainObject>();
    }

    public AbstractDomainObject cache(AbstractDomainObject obj) {
        String key = obj.getOid();
        AbstractDomainObject oldValue = this.cache.get(key);

        if (oldValue != null) {
            return oldValue;
        } else {
            this.cache.put(key, obj);
            return obj;
        }
    }

    public AbstractDomainObject lookup(String key) {
        return this.cache.get(key);
    }

}
