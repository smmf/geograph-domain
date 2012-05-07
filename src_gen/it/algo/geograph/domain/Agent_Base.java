package it.algo.geograph.domain;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class Agent_Base extends org.cloudtm.framework.ispn.AbstractDomainObject {
    public static dml.runtime.Role<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root> role$$root = new dml.runtime.Role<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root>() {
        public void add(it.algo.geograph.domain.Agent o1, it.algo.geograph.domain.Root o2, dml.runtime.Relation<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root> relation) {
            if (o1 != null) {
                it.algo.geograph.domain.Root old2 = o1.getRoot();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setRoot$unidirectional(o2);
                }
            }
        }
        public void remove(it.algo.geograph.domain.Agent o1, it.algo.geograph.domain.Root o2) {
            if (o1 != null) {
                o1.setRoot$unidirectional(null);
            }
        }
        public dml.runtime.Role<it.algo.geograph.domain.Root,it.algo.geograph.domain.Agent> getInverseRole() {
            return it.algo.geograph.domain.Root.role$$agents;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject> role$$geoObjects = new dml.runtime.RoleMany<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.Agent o1) {
            return (org.cloudtm.framework.ispn.RelationSet<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject>)o1.getGeoObjects();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$agent;
        }
        
    };
    public static dml.runtime.DirectRelation<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root> RootHasAgents = new dml.runtime.DirectRelation<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root>(role$$root);
    static {
        it.algo.geograph.domain.Root.RootHasAgents = RootHasAgents.getInverseRelation();
    }
    public static dml.runtime.Relation<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject> AgentHasGeoObjects;
    
    public  Agent_Base() {
        
    }
    
    public java.lang.String getStatus() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":status");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.lang.String)obj;
    }
    
    public void setStatus(java.lang.String status) {
        IspnTxManager.cachePut(getOid() + ":status", (status == null ? NULL_OBJECT : status));
    }
    
    public java.lang.String getUser() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":user");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.lang.String)obj;
    }
    
    public void setUser(java.lang.String user) {
        IspnTxManager.cachePut(getOid() + ":user", (user == null ? NULL_OBJECT : user));
    }
    
    public it.algo.geograph.domain.Root getRoot() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":root");
        return (oid == null || oid instanceof NullClass ? null : (it.algo.geograph.domain.Root)fromOid((String)oid));
    }
    
    public void setRoot(it.algo.geograph.domain.Root root) {
        RootHasAgents.add((it.algo.geograph.domain.Agent)this, root);
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
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getGeoObjects() {
        BPlusTree<it.algo.geograph.domain.GeoObject> internalSet;
        Object oid = IspnTxManager.cacheGet(getOid() + ":geoObjects");
        if (oid == null || oid instanceof NullClass) {
            internalSet = new BPlusTree<it.algo.geograph.domain.GeoObject>();
            IspnTxManager.staticSave(internalSet);
            IspnTxManager.cachePut(getOid() + ":geoObjects", internalSet.getOid());
        } else {
            internalSet = (BPlusTree<it.algo.geograph.domain.GeoObject>)fromOid((String)oid);
            // no need to test for null.  The entry must exist for sure.
        }
        return new org.cloudtm.framework.ispn.RelationSet(this, AgentHasGeoObjects, internalSet);
    }
    
    public void addGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        AgentHasGeoObjects.add((it.algo.geograph.domain.Agent)this, geoObjects);
    }
    
    public void removeGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        AgentHasGeoObjects.remove((it.algo.geograph.domain.Agent)this, geoObjects);
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getGeoObjectsSet() {
        return getGeoObjects();
    }
    
    public int getGeoObjectsCount() {
        return getGeoObjects().size();
    }
    
    public boolean hasAnyGeoObjects() {
        return (getGeoObjects().size() != 0);
    }
    
    public boolean hasGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        return getGeoObjects().contains(geoObjects);
    }
    
    public java.util.Iterator<it.algo.geograph.domain.GeoObject> getGeoObjectsIterator() {
        return getGeoObjects().iterator();
    }
    
}
