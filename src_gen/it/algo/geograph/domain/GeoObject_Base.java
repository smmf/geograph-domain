package it.algo.geograph.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class GeoObject_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public static pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent> role$$agent = new pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent>() {
        public it.algo.geograph.domain.Agent getValue(it.algo.geograph.domain.GeoObject o1) {
            return ((GeoObject_Base.DO_State)o1.get$obj$state(false)).agent;
        }
        public void setValue(it.algo.geograph.domain.GeoObject o1, it.algo.geograph.domain.Agent o2) {
            ((GeoObject_Base.DO_State)o1.get$obj$state(true)).agent = o2;
        }
        public dml.runtime.Role<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.Agent.role$$geoObjects;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> role$$outcoming = new dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.GeoObject o1) {
            return ((GeoObject_Base)o1).get$rl$outcoming();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$incoming;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> role$$incoming = new dml.runtime.RoleMany<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.GeoObject o1) {
            return ((GeoObject_Base)o1).get$rl$incoming();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$outcoming;
        }
        
    };
    public static pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> role$$root = new pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root>() {
        public it.algo.geograph.domain.Root getValue(it.algo.geograph.domain.GeoObject o1) {
            return ((GeoObject_Base.DO_State)o1.get$obj$state(false)).root;
        }
        public void setValue(it.algo.geograph.domain.GeoObject o1, it.algo.geograph.domain.Root o2) {
            ((GeoObject_Base.DO_State)o1.get$obj$state(true)).root = o2;
        }
        public dml.runtime.Role<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject> getInverseRole() {
            return it.algo.geograph.domain.Root.role$$geoObjects;
        }
        
    };
    public static pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent> AgentHasGeoObjects = new pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent>(role$$agent);
    static {
        it.algo.geograph.domain.Agent.AgentHasGeoObjects = AgentHasGeoObjects.getInverseRelation();
    }
    
    static {
        AgentHasGeoObjects.setRelationName("it.algo.geograph.domain.GeoObject.AgentHasGeoObjects");
    }
    public static dml.runtime.Relation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> GeoObjectHasEdges$Inverse;
    public static pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> GeoObjectHasEdges = new pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>(role$$incoming);
    static {
        it.algo.geograph.domain.GeoObject.GeoObjectHasEdges$Inverse = GeoObjectHasEdges.getInverseRelation();
    }
    
    static {
        GeoObjectHasEdges.setRelationName("it.algo.geograph.domain.GeoObject.GeoObjectHasEdges");
        GeoObjectHasEdges.addListener(new dml.runtime.RelationAdapter<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject>() {
            @Override
            public void beforeAdd(it.algo.geograph.domain.GeoObject arg0, it.algo.geograph.domain.GeoObject arg1) {
                pt.ist.fenixframework.pstm.Transaction.addRelationTuple("GeoObjectHasEdges", arg1, "outcoming", arg0, "incoming");
            }
            @Override
            public void beforeRemove(it.algo.geograph.domain.GeoObject arg0, it.algo.geograph.domain.GeoObject arg1) {
                pt.ist.fenixframework.pstm.Transaction.removeRelationTuple("GeoObjectHasEdges", arg1, "outcoming", arg0, "incoming");
            }
            
        }
        );
    }
    public static pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> RootHasGeoObjects = new pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root>(role$$root);
    static {
        it.algo.geograph.domain.Root.RootHasGeoObjects = RootHasGeoObjects.getInverseRelation();
    }
    
    static {
        RootHasGeoObjects.setRelationName("it.algo.geograph.domain.GeoObject.RootHasGeoObjects");
    }
    
    
    private RelationList<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> get$rl$outcoming() {
        return get$$relationList("outcoming", GeoObjectHasEdges$Inverse);
        
    }
    private RelationList<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.GeoObject> get$rl$incoming() {
        return get$$relationList("incoming", GeoObjectHasEdges);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  GeoObject_Base() {
        super();
    }
    
    public java.math.BigDecimal getLatitude() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "latitude");
        return ((DO_State)this.get$obj$state(false)).latitude;
    }
    
    public void setLatitude(java.math.BigDecimal latitude) {
        ((DO_State)this.get$obj$state(true)).latitude = latitude;
    }
    
    public java.math.BigDecimal getLongitude() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "longitude");
        return ((DO_State)this.get$obj$state(false)).longitude;
    }
    
    public void setLongitude(java.math.BigDecimal longitude) {
        ((DO_State)this.get$obj$state(true)).longitude = longitude;
    }
    
    public java.lang.String getBody() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "body");
        return ((DO_State)this.get$obj$state(false)).body;
    }
    
    public void setBody(java.lang.String body) {
        ((DO_State)this.get$obj$state(true)).body = body;
    }
    
    public java.lang.String getType() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "type");
        return ((DO_State)this.get$obj$state(false)).type;
    }
    
    public void setType(java.lang.String type) {
        ((DO_State)this.get$obj$state(true)).type = type;
    }
    
    public it.algo.geograph.domain.Agent getAgent() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "agent");
        return ((DO_State)this.get$obj$state(false)).agent;
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
    
    public int getOutcomingCount() {
        return get$rl$outcoming().size();
    }
    
    public boolean hasAnyOutcoming() {
        return (! get$rl$outcoming().isEmpty());
    }
    
    public boolean hasOutcoming(it.algo.geograph.domain.GeoObject outcoming) {
        return get$rl$outcoming().contains(outcoming);
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getOutcomingSet() {
        return get$rl$outcoming();
    }
    
    public void addOutcoming(it.algo.geograph.domain.GeoObject outcoming) {
        GeoObjectHasEdges$Inverse.add((it.algo.geograph.domain.GeoObject)this, outcoming);
    }
    
    public void removeOutcoming(it.algo.geograph.domain.GeoObject outcoming) {
        GeoObjectHasEdges$Inverse.remove((it.algo.geograph.domain.GeoObject)this, outcoming);
    }
    
    public java.util.List<it.algo.geograph.domain.GeoObject> getOutcoming() {
        return get$rl$outcoming();
    }
    
    public java.util.Iterator<it.algo.geograph.domain.GeoObject> getOutcomingIterator() {
        return get$rl$outcoming().iterator();
    }
    
    public int getIncomingCount() {
        return get$rl$incoming().size();
    }
    
    public boolean hasAnyIncoming() {
        return (! get$rl$incoming().isEmpty());
    }
    
    public boolean hasIncoming(it.algo.geograph.domain.GeoObject incoming) {
        return get$rl$incoming().contains(incoming);
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getIncomingSet() {
        return get$rl$incoming();
    }
    
    public void addIncoming(it.algo.geograph.domain.GeoObject incoming) {
        GeoObjectHasEdges.add((it.algo.geograph.domain.GeoObject)this, incoming);
    }
    
    public void removeIncoming(it.algo.geograph.domain.GeoObject incoming) {
        GeoObjectHasEdges.remove((it.algo.geograph.domain.GeoObject)this, incoming);
    }
    
    public java.util.List<it.algo.geograph.domain.GeoObject> getIncoming() {
        return get$rl$incoming();
    }
    
    public java.util.Iterator<it.algo.geograph.domain.GeoObject> getIncomingIterator() {
        return get$rl$incoming().iterator();
    }
    
    public it.algo.geograph.domain.Root getRoot() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "root");
        return ((DO_State)this.get$obj$state(false)).root;
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
    
    protected boolean checkDisconnected() {
        if (hasAgent()) return false;
        if (hasAnyOutcoming()) return false;
        if (hasAnyIncoming()) return false;
        if (hasRoot()) return false;
        return true;
        
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("outcoming")) return GeoObjectHasEdges$Inverse;
        if (attrName.equals("incoming")) return GeoObjectHasEdges;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("outcoming", GeoObjectHasEdges$Inverse);
        get$$relationList("incoming", GeoObjectHasEdges);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private java.math.BigDecimal latitude;
        private java.math.BigDecimal longitude;
        private java.lang.String body;
        private java.lang.String type;
        private it.algo.geograph.domain.Agent agent;
        private it.algo.geograph.domain.Root root;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.latitude = this.latitude;
            newCasted.longitude = this.longitude;
            newCasted.body = this.body;
            newCasted.type = this.type;
            newCasted.agent = this.agent;
            newCasted.root = this.root;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private Serialized$Decimal latitude;
            private Serialized$Decimal longitude;
            private java.lang.String body;
            private java.lang.String type;
            private it.algo.geograph.domain.Agent agent;
            private it.algo.geograph.domain.Root root;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.latitude = pt.ist.fenixframework.ValueTypeSerializationGenerator.serialize$Decimal(obj.latitude);
                this.longitude = pt.ist.fenixframework.ValueTypeSerializationGenerator.serialize$Decimal(obj.longitude);
                this.body = obj.body;
                this.type = obj.type;
                this.agent = obj.agent;
                this.root = obj.root;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.latitude = pt.ist.fenixframework.ValueTypeSerializationGenerator.deSerialize$Decimal(this.latitude);
                state.longitude = pt.ist.fenixframework.ValueTypeSerializationGenerator.deSerialize$Decimal(this.longitude);
                state.body = this.body;
                state.type = this.type;
                state.agent = this.agent;
                state.root = this.root;
                
            }
            
        }
        
    }
    
}
