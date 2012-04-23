package it.algo.geograph.domain;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class Root_Base extends org.cloudtm.framework.ispn.AbstractDomainObject {
    public static dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.Agent> role$$agents = new dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.Agent>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.Agent> getSet(it.algo.geograph.domain.Root o1) {
            return (org.cloudtm.framework.ispn.RelationSet<it.algo.geograph.domain.Root,it.algo.geograph.domain.Agent>)o1.getAgents();
        }
        public dml.runtime.Role<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root> getInverseRole() {
            return it.algo.geograph.domain.Agent.role$$root;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.Job> role$$jobs = new dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.Job>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.Job> getSet(it.algo.geograph.domain.Root o1) {
            return (org.cloudtm.framework.ispn.RelationSet<it.algo.geograph.domain.Root,it.algo.geograph.domain.Job>)o1.getJobs();
        }
        public dml.runtime.Role<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root> getInverseRole() {
            return it.algo.geograph.domain.Job.role$$root;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject> role$$geoObjects = new dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.Root o1) {
            return (org.cloudtm.framework.ispn.RelationSet<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject>)o1.getGeoObjects();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$root;
        }
        
    };
    public static dml.runtime.Relation<it.algo.geograph.domain.Root,it.algo.geograph.domain.Agent> RootHasAgents;
    public static dml.runtime.Relation<it.algo.geograph.domain.Root,it.algo.geograph.domain.Job> RootHasJobs;
    public static dml.runtime.Relation<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject> RootHasGeoObjects;
    
    public  Root_Base() {
        
    }
    
    public boolean getLoaded() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":loaded");
        if (obj == null || obj instanceof NullClass) return false;
        return (Boolean)obj;
    }
    
    public void setLoaded(boolean loaded) {
        IspnTxManager.cachePut(getOid() + ":loaded", loaded);
    }
    
    public java.lang.Integer getNumGeoObjectIds() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":numGeoObjectIds");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.lang.Integer)obj;
    }
    
    public void setNumGeoObjectIds(java.lang.Integer numGeoObjectIds) {
        IspnTxManager.cachePut(getOid() + ":numGeoObjectIds", (numGeoObjectIds == null ? NULL_OBJECT : numGeoObjectIds));
    }
    
    public java.util.Set<it.algo.geograph.domain.Agent> getAgents() {
        BPlusTree<it.algo.geograph.domain.Agent> internalSet;
        Object oid = IspnTxManager.cacheGet(getOid() + ":agents");
        if (oid == null || oid instanceof NullClass) {
            internalSet = new BPlusTree<it.algo.geograph.domain.Agent>();
            IspnTxManager.staticSave(internalSet);
            IspnTxManager.cachePut(getOid() + ":agents", internalSet.getOid());
        } else {
            internalSet = (BPlusTree<it.algo.geograph.domain.Agent>)fromOid((String)oid);
            // no need to test for null.  The entry must exist for sure.
        }
        return new org.cloudtm.framework.ispn.RelationSet(this, RootHasAgents, internalSet);
    }
    
    public void addAgents(it.algo.geograph.domain.Agent agents) {
        RootHasAgents.add((it.algo.geograph.domain.Root)this, agents);
    }
    
    public void removeAgents(it.algo.geograph.domain.Agent agents) {
        RootHasAgents.remove((it.algo.geograph.domain.Root)this, agents);
    }
    
    public java.util.Set<it.algo.geograph.domain.Agent> getAgentsSet() {
        return getAgents();
    }
    
    public int getAgentsCount() {
        return getAgents().size();
    }
    
    public boolean hasAnyAgents() {
        return (getAgents().size() != 0);
    }
    
    public boolean hasAgents(it.algo.geograph.domain.Agent agents) {
        return getAgents().contains(agents);
    }
    
    public java.util.Iterator<it.algo.geograph.domain.Agent> getAgentsIterator() {
        return getAgents().iterator();
    }
    
    public java.util.Set<it.algo.geograph.domain.Job> getJobs() {
        BPlusTree<it.algo.geograph.domain.Job> internalSet;
        Object oid = IspnTxManager.cacheGet(getOid() + ":jobs");
        if (oid == null || oid instanceof NullClass) {
            internalSet = new BPlusTree<it.algo.geograph.domain.Job>();
            IspnTxManager.staticSave(internalSet);
            IspnTxManager.cachePut(getOid() + ":jobs", internalSet.getOid());
        } else {
            internalSet = (BPlusTree<it.algo.geograph.domain.Job>)fromOid((String)oid);
            // no need to test for null.  The entry must exist for sure.
        }
        return new org.cloudtm.framework.ispn.RelationSet(this, RootHasJobs, internalSet);
    }
    
    public void addJobs(it.algo.geograph.domain.Job jobs) {
        RootHasJobs.add((it.algo.geograph.domain.Root)this, jobs);
    }
    
    public void removeJobs(it.algo.geograph.domain.Job jobs) {
        RootHasJobs.remove((it.algo.geograph.domain.Root)this, jobs);
    }
    
    public java.util.Set<it.algo.geograph.domain.Job> getJobsSet() {
        return getJobs();
    }
    
    public int getJobsCount() {
        return getJobs().size();
    }
    
    public boolean hasAnyJobs() {
        return (getJobs().size() != 0);
    }
    
    public boolean hasJobs(it.algo.geograph.domain.Job jobs) {
        return getJobs().contains(jobs);
    }
    
    public java.util.Iterator<it.algo.geograph.domain.Job> getJobsIterator() {
        return getJobs().iterator();
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
        return new org.cloudtm.framework.ispn.RelationSet(this, RootHasGeoObjects, internalSet);
    }
    
    public void addGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        RootHasGeoObjects.add((it.algo.geograph.domain.Root)this, geoObjects);
    }
    
    public void removeGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        RootHasGeoObjects.remove((it.algo.geograph.domain.Root)this, geoObjects);
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
