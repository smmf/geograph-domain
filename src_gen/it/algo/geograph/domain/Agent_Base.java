package it.algo.geograph.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Agent_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public static pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root> role$$root = new pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root>() {
        public it.algo.geograph.domain.Root getValue(it.algo.geograph.domain.Agent o1) {
            return ((Agent_Base.DO_State)o1.get$obj$state(false)).root;
        }
        public void setValue(it.algo.geograph.domain.Agent o1, it.algo.geograph.domain.Root o2) {
            ((Agent_Base.DO_State)o1.get$obj$state(true)).root = o2;
        }
        public dml.runtime.Role<it.algo.geograph.domain.Root,it.algo.geograph.domain.Agent> getInverseRole() {
            return it.algo.geograph.domain.Root.role$$agents;
        }
        
    };
    public static dml.runtime.RoleMany<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject> role$$geoObjects = new dml.runtime.RoleMany<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.Agent o1) {
            return ((Agent_Base)o1).get$rl$geoObjects();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Agent> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$agent;
        }
        
    };
    public static pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root> RootHasAgents = new pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.Agent,it.algo.geograph.domain.Root>(role$$root);
    static {
        it.algo.geograph.domain.Root.RootHasAgents = RootHasAgents.getInverseRelation();
    }
    
    static {
        RootHasAgents.setRelationName("it.algo.geograph.domain.Agent.RootHasAgents");
    }
    public static dml.runtime.Relation<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject> AgentHasGeoObjects;
    
    
    private RelationList<it.algo.geograph.domain.Agent,it.algo.geograph.domain.GeoObject> get$rl$geoObjects() {
        return get$$relationList("geoObjects", AgentHasGeoObjects);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Agent_Base() {
        super();
    }
    
    public java.lang.String getStatus() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "status");
        return ((DO_State)this.get$obj$state(false)).status;
    }
    
    public void setStatus(java.lang.String status) {
        ((DO_State)this.get$obj$state(true)).status = status;
    }
    
    public it.algo.geograph.domain.Root getRoot() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "root");
        return ((DO_State)this.get$obj$state(false)).root;
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
    
    public int getGeoObjectsCount() {
        return get$rl$geoObjects().size();
    }
    
    public boolean hasAnyGeoObjects() {
        return (! get$rl$geoObjects().isEmpty());
    }
    
    public boolean hasGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        return get$rl$geoObjects().contains(geoObjects);
    }
    
    public java.util.Set<it.algo.geograph.domain.GeoObject> getGeoObjectsSet() {
        return get$rl$geoObjects();
    }
    
    public void addGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        AgentHasGeoObjects.add((it.algo.geograph.domain.Agent)this, geoObjects);
    }
    
    public void removeGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        AgentHasGeoObjects.remove((it.algo.geograph.domain.Agent)this, geoObjects);
    }
    
    public java.util.List<it.algo.geograph.domain.GeoObject> getGeoObjects() {
        return get$rl$geoObjects();
    }
    
    public java.util.Iterator<it.algo.geograph.domain.GeoObject> getGeoObjectsIterator() {
        return get$rl$geoObjects().iterator();
    }
    
    protected boolean checkDisconnected() {
        if (hasRoot()) return false;
        if (hasAnyGeoObjects()) return false;
        return true;
        
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("geoObjects")) return AgentHasGeoObjects;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("geoObjects", AgentHasGeoObjects);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private java.lang.String status;
        private it.algo.geograph.domain.Root root;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.status = this.status;
            newCasted.root = this.root;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private java.lang.String status;
            private it.algo.geograph.domain.Root root;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.status = obj.status;
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
                state.status = this.status;
                state.root = this.root;
                
            }
            
        }
        
    }
    
}
