package it.algo.geograph.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.serialization.ValueTypeSerializationGenerator.*;
public abstract class GeoObject_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
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
    public static pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root> RootHasGeoObjects = new pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.GeoObject,it.algo.geograph.domain.Root>(role$$root);
    static {
        it.algo.geograph.domain.Root.RootHasGeoObjects = RootHasGeoObjects.getInverseRelation();
    }
    
    static {
        RootHasGeoObjects.setRelationName("it.algo.geograph.domain.GeoObject.RootHasGeoObjects");
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
        if (hasRoot()) return false;
        return true;
        
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private java.math.BigDecimal latitude;
        private java.math.BigDecimal longitude;
        private it.algo.geograph.domain.Root root;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.latitude = this.latitude;
            newCasted.longitude = this.longitude;
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
            private it.algo.geograph.domain.Root root;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.latitude = pt.ist.fenixframework.serialization.ValueTypeSerializationGenerator.serialize$Decimal(obj.latitude);
                this.longitude = pt.ist.fenixframework.serialization.ValueTypeSerializationGenerator.serialize$Decimal(obj.longitude);
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
                state.latitude = pt.ist.fenixframework.serialization.ValueTypeSerializationGenerator.deSerialize$Decimal(this.latitude);
                state.longitude = pt.ist.fenixframework.serialization.ValueTypeSerializationGenerator.deSerialize$Decimal(this.longitude);
                state.root = this.root;
                
            }
            
        }
        
    }
    
}
