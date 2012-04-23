package it.algo.geograph.domain;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class Job_Base extends org.cloudtm.framework.ispn.AbstractDomainObject {
    public static dml.runtime.Role<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root> role$$root = new dml.runtime.Role<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root>() {
        public void add(it.algo.geograph.domain.Job o1, it.algo.geograph.domain.Root o2, dml.runtime.Relation<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root> relation) {
            if (o1 != null) {
                it.algo.geograph.domain.Root old2 = o1.getRoot();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setRoot$unidirectional(o2);
                }
            }
        }
        public void remove(it.algo.geograph.domain.Job o1, it.algo.geograph.domain.Root o2) {
            if (o1 != null) {
                o1.setRoot$unidirectional(null);
            }
        }
        public dml.runtime.Role<it.algo.geograph.domain.Root,it.algo.geograph.domain.Job> getInverseRole() {
            return it.algo.geograph.domain.Root.role$$jobs;
        }
        
    };
    public static dml.runtime.DirectRelation<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root> RootHasJobs = new dml.runtime.DirectRelation<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root>(role$$root);
    static {
        it.algo.geograph.domain.Root.RootHasJobs = RootHasJobs.getInverseRelation();
    }
    
    public  Job_Base() {
        
    }
    
    public java.lang.String getName() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":name");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.lang.String)obj;
    }
    
    public void setName(java.lang.String name) {
        IspnTxManager.cachePut(getOid() + ":name", (name == null ? NULL_OBJECT : name));
    }
    
    public boolean getEnabled() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":enabled");
        if (obj == null || obj instanceof NullClass) return false;
        return (Boolean)obj;
    }
    
    public void setEnabled(boolean enabled) {
        IspnTxManager.cachePut(getOid() + ":enabled", enabled);
    }
    
    public java.lang.Integer getDistance() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":distance");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.lang.Integer)obj;
    }
    
    public void setDistance(java.lang.Integer distance) {
        IspnTxManager.cachePut(getOid() + ":distance", (distance == null ? NULL_OBJECT : distance));
    }
    
    public it.algo.geograph.domain.Root getRoot() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":root");
        return (oid == null || oid instanceof NullClass ? null : (it.algo.geograph.domain.Root)fromOid((String)oid));
    }
    
    public void setRoot(it.algo.geograph.domain.Root root) {
        RootHasJobs.add((it.algo.geograph.domain.Job)this, root);
    }
    
    public boolean hasRoot() {
        return (getRoot() != null);
    }
    
    public void removeRoot() {
        setRoot(null);
    }
    
    public void setRoot$unidirectional(it.algo.geograph.domain.Root root) {
        IspnTxManager.cachePut(getOid() + ":root", (root == null ? NULL_OBJECT : root.getOid()));
    }
    
}
