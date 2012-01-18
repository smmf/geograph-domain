package org.cloudtm.framework.ogm;

//import it.algo.geograph.TxSystem;
//import it.algo.geograph.TxManager;

public abstract class AbstractDomainObject {
    private long oid;

    private static int unsafe_count = 0;
    public AbstractDomainObject() {
        // TxManager txManager = TxSystem.getManager();
        // if (txManager == null) {
        //     // System.out.println("This needs to be fixed.  SKIPPING!!!!!!!!!!!!!!!!!!! " + unsafe_count); return;
        //     // try {Thread.sleep(5000);} catch (Exception e) {}
        //     System.out.println("NULL TX MANAGER??!!!");
        //     new Exception().printStackTrace();
        //     try {Thread.sleep(5000);} catch (Exception e) {}
        // }
        // txManager.save(this);
    }

    public long getOid() {
        return this.oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

}
