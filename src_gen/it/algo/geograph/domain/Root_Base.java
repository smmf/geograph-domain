package it.algo.geograph.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Root_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public static dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject> role$$geoObjects = new dml.runtime.RoleMany<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject>() {
        public dml.runtime.RelationBaseSet<it.algo.geograph.domain.GeoObject> getSet(it.algo.geograph.domain.Root o1) {
            return ((Root_Base)o1).get$rl$geoObjects();
        }
        public dml.runtime.Role<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> getInverseRole() {
            return it.algo.geograph.domain.GeoObject.role$$root;
        }
        
    };
    public static dml.runtime.Relation<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject> RootHasGeoObjects;
    
    
    private RelationList<it.algo.geograph.domain.Root,it.algo.geograph.domain.GeoObject> get$rl$geoObjects() {
        return get$$relationList("geoObjects", RootHasGeoObjects);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Root_Base() {
        super();
    }
    
    public boolean getLoaded() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "loaded");
        return ((DO_State)this.get$obj$state(false)).loaded;
    }
    
    public void setLoaded(boolean loaded) {
        ((DO_State)this.get$obj$state(true)).loaded = loaded;
    }
    
    public java.lang.Integer getNumGeoObjectIds() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "numGeoObjectIds");
        return ((DO_State)this.get$obj$state(false)).numGeoObjectIds;
    }
    
    public void setNumGeoObjectIds(java.lang.Integer numGeoObjectIds) {
        ((DO_State)this.get$obj$state(true)).numGeoObjectIds = numGeoObjectIds;
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
        RootHasGeoObjects.add((it.algo.geograph.domain.Root)this, geoObjects);
    }
    
    public void removeGeoObjects(it.algo.geograph.domain.GeoObject geoObjects) {
        RootHasGeoObjects.remove((it.algo.geograph.domain.Root)this, geoObjects);
    }
    
    public java.util.List<it.algo.geograph.domain.GeoObject> getGeoObjects() {
        return get$rl$geoObjects();
    }
    
    public java.util.Iterator<it.algo.geograph.domain.GeoObject> getGeoObjectsIterator() {
        return get$rl$geoObjects().iterator();
    }
    
    protected boolean checkDisconnected() {
        if (hasAnyGeoObjects()) return false;
        return true;
        
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("geoObjects")) return RootHasGeoObjects;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("geoObjects", RootHasGeoObjects);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private boolean loaded;
        private java.lang.Integer numGeoObjectIds;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.loaded = this.loaded;
            newCasted.numGeoObjectIds = this.numGeoObjectIds;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private boolean loaded;
            private java.lang.Integer numGeoObjectIds;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.loaded = obj.loaded;
                this.numGeoObjectIds = obj.numGeoObjectIds;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.loaded = this.loaded;
                state.numGeoObjectIds = this.numGeoObjectIds;
                
            }
            
        }
        
    }
    
}
