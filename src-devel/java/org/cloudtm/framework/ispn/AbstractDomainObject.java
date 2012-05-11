package org.cloudtm.framework.ispn;

import java.util.UUID;
import org.cloudtm.framework.ispn.IspnTxManager;

public abstract class AbstractDomainObject implements java.io.Serializable {
    public static final class NullClass implements java.io.Serializable {}
    public static final NullClass NULL_OBJECT = new NullClass();

    protected String oid;

    public AbstractDomainObject() {
        this.oid = UUID.randomUUID().toString();
    }

    public String getOid() {
        return this.oid;
    }

    /** DO NOT USE UNLESS YOU KNOW WHAT YOU ARE DOING.  This method changes the ID of a domain
     * object, which is a highly unsafe operation to perform when the object has already been published to anyone else
     * after its creation.
     *
     * The goal is to enable the creator of a "root" object to set a well-known object ID. */
    public AbstractDomainObject set$Oid(String oid) {
        this.oid = oid;
        return this;
    }

    public static <T extends AbstractDomainObject> T fromOid(String oid) {
        AbstractDomainObject ado = IspnTxManager.getIdentityMap().lookup(oid);

        if (ado == null) {
            Object obj = IspnTxManager.cacheGet(oid);
            if (obj == null || obj instanceof NullClass) {
                return null;
            }
            ado = IspnTxManager.getIdentityMap().cache((AbstractDomainObject)obj);
        }
        return (T) ado;
    }
}
