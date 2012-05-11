package it.algo.geograph.domain;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class GeoObject_Base extends org.cloudtm.framework.ispn.AbstractDomainObject {
    public static dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent> role$$agent = new dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent>() {
        public void add(it.algo.geograph.domain.GeoObject o1, it.algo.geograph.domain.Agent o2, dml.runtime.Relation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent> relation) {
            if (o1 != null) {
                it.algo.geograph.domain.Agent old2 = o1.getAgent();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setAgent$unidirectional(o2);
                }
            }
        }
        public void remove(it.algo.geograph.domain.GeoObject o1, it.algo.geograph.domain.Agent o2) {
            if (o1 != null) {
                o1.setAgent$unidirectional(null);
            }
        }
        public dml.runtime.Role<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.Agent.role$$geoObjects;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> role$$outcoming = new dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.GeoObject o1) {
            return (org.cloudtm.framework.ispn.RelationSet<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>)o1.getOutcoming();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$incoming;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> role$$incoming = new dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.GeoObject o1) {
            return (org.cloudtm.framework.ispn.RelationSet<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>)o1.getIncoming();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$outcoming;
        }
        
    };
    public static dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> role$$root = new dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root>() {
        public void add(it.algo.geograph.domain.GeoObject o1, it.algo.geograph.domain.Root o2, dml.runtime.Relation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> relation) {
            if (o1 != null) {
                it.algo.geograph.domain.Root old2 = o1.getRoot();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setRoot$unidirectional(o2);
                }
            }
        }
        public void remove(it.algo.geograph.domain.GeoObject o1, it.algo.geograph.domain.Root o2) {
            if (o1 != null) {
                o1.setRoot$unidirectional(null);
            }
        }
        public dml.runtime.Role<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.Root.role$$geoObjects;
        }
        
    };
    public static dml.runtime.DirectRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent> AgentHasGeoObjects = new dml.runtime.DirectRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent>(role$$agent);
    static {
        it.algo.geograph.domain.Agent.AgentHasGeoObjects = AgentHasGeoObjects.getInverseRelation();
    }
    public static dml.runtime.Relation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> GeoObjectHasEdges$Inverse;
    public static dml.runtime.DirectRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> GeoObjectHasEdges = new dml.runtime.DirectRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>(role$$incoming);
    static {
        it.algo.geograph.domain.GeoObject.GeoObjectHasEdges$Inverse = GeoObjectHasEdges.getInverseRelation();
    }
    public static dml.runtime.DirectRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> RootHasGeoObjects = new dml.runtime.DirectRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root>(role$$root);
    static {
        it.algo.geograph.domain.Root.RootHasGeoObjects = RootHasGeoObjects.getInverseRelation();
    }
    
    public  GeoObject_Base() {
        
    }
    
    public java.math.BigDecimal getLatitude() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":latitude");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.math.BigDecimal)obj;
    }
    
    public void setLatitude(java.math.BigDecimal latitude) {
        IspnTxManager.cachePut(getOid() + ":latitude", (latitude == null ? NULL_OBJECT : latitude));
    }
    
    public java.math.BigDecimal getLongitude() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":longitude");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.math.BigDecimal)obj;
    }
    
    public void setLongitude(java.math.BigDecimal longitude) {
        IspnTxManager.cachePut(getOid() + ":longitude", (longitude == null ? NULL_OBJECT : longitude));
    }
    
    public java.lang.String getBody() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":body");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.lang.String)obj;
    }
    
    public void setBody(java.lang.String body) {
        IspnTxManager.cachePut(getOid() + ":body", (body == null ? NULL_OBJECT : body));
    }
    
    public java.lang.String getType() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":type");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.lang.String)obj;
    }
    
    public void setType(java.lang.String type) {
        IspnTxManager.cachePut(getOid() + ":type", (type == null ? NULL_OBJECT : type));
    }
    
    public it.algo.geograph.domain.Agent getAgent() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":agent");
        return (oid == null || oid instanceof NullClass ? null : (it.algo.geograph.domain.Agent)fromOid((String)oid));
    }
    
    public void setAgent(it.algo.geograph.domain.Agent agent) {
        AgentHasGeoObjects.add((it.algo.geograph.domain.GeoObject)this, agent);
    }
    
    public boolean hasAgent() {
        return (getAgent() != null);
    }
    
    public void removeAgent() {
        setAgent(null);
    }
    
    public void setAgent$unidirectional(it.algo.geograph.domain.Agent agent) {
        IspnTxManager.cachePut(getOid() + ":agent", (agent == null ? NULL_OBJECT : agent.getOid()));
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getOutcoming() {
        BPlusTree<it.algo.geograph.domain.GeoObject> internalSet;
        Object oid = IspnTxManager.cacheGet(getOid() + ":outcoming");
        if (oid == null || oid instanceof NullClass) {
            internalSet = new BPlusTree<it.algo.geograph.domain.GeoObject>();
            internalSet.initRoot();
            IspnTxManager.staticSave(internalSet);
            IspnTxManager.cachePut(getOid() + ":outcoming", internalSet.getOid());
        } else {
            internalSet = (BPlusTree<it.algo.geograph.domain.GeoObject>)fromOid((String)oid);
            // no need to test for null.  The entry must exist for sure.
        }
        return new org.cloudtm.framework.ispn.RelationSet(this, GeoObjectHasEdges$Inverse, internalSet);
    }
    
    public void addOutcoming(it.algo.geograph.domain.GeoObject outcoming) {
        GeoObjectHasEdges$Inverse.add((it.algo.geograph.domain.GeoObject)this, outcoming);
    }
    
    public void removeOutcoming(it.algo.geograph.domain.GeoObject outcoming) {
        GeoObjectHasEdges$Inverse.remove((it.algo.geograph.domain.GeoObject)this, outcoming);
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getOutcomingSet() {
        return getOutcoming();
    }
    
    public int getOutcomingCount() {
        return getOutcoming().size();
    }
    
    public boolean hasAnyOutcoming() {
        return (getOutcoming().size() != 0);
    }
    
    public boolean hasOutcoming(it.algo.geograph.domain.GeoObject outcoming) {
        return getOutcoming().contains(outcoming);
    }
    
    public java.util.Iterator<it.algo.geograph.domain.GeoObject> getOutcomingIterator() {
        return getOutcoming().iterator();
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getIncoming() {
        BPlusTree<it.algo.geograph.domain.GeoObject> internalSet;
        Object oid = IspnTxManager.cacheGet(getOid() + ":incoming");
        if (oid == null || oid instanceof NullClass) {
            internalSet = new BPlusTree<it.algo.geograph.domain.GeoObject>();
            internalSet.initRoot();
            IspnTxManager.staticSave(internalSet);
            IspnTxManager.cachePut(getOid() + ":incoming", internalSet.getOid());
        } else {
            internalSet = (BPlusTree<it.algo.geograph.domain.GeoObject>)fromOid((String)oid);
            // no need to test for null.  The entry must exist for sure.
        }
        return new org.cloudtm.framework.ispn.RelationSet(this, GeoObjectHasEdges, internalSet);
    }
    
    public void addIncoming(it.algo.geograph.domain.GeoObject incoming) {
        GeoObjectHasEdges.add((it.algo.geograph.domain.GeoObject)this, incoming);
    }
    
    public void removeIncoming(it.algo.geograph.domain.GeoObject incoming) {
        GeoObjectHasEdges.remove((it.algo.geograph.domain.GeoObject)this, incoming);
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getIncomingSet() {
        return getIncoming();
    }
    
    public int getIncomingCount() {
        return getIncoming().size();
    }
    
    public boolean hasAnyIncoming() {
        return (getIncoming().size() != 0);
    }
    
    public boolean hasIncoming(it.algo.geograph.domain.GeoObject incoming) {
        return getIncoming().contains(incoming);
    }
    
    public java.util.Iterator<it.algo.geograph.domain.GeoObject> getIncomingIterator() {
        return getIncoming().iterator();
    }
    
    public it.algo.geograph.domain.Root getRoot() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":root");
        return (oid == null || oid instanceof NullClass ? null : (it.algo.geograph.domain.Root)fromOid((String)oid));
    }
    
    public void setRoot(it.algo.geograph.domain.Root root) {
        RootHasGeoObjects.add((it.algo.geograph.domain.GeoObject)this, root);
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
