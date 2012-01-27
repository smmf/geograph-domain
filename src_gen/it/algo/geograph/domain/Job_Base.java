package it.algo.geograph.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Job_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public static pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root> role$$root = new pt.ist.fenixframework.pstm.dml.RoleOne<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root>() {
        public it.algo.geograph.domain.Root getValue(it.algo.geograph.domain.Job o1) {
            return ((Job_Base.DO_State)o1.get$obj$state(false)).root;
        }
        public void setValue(it.algo.geograph.domain.Job o1, it.algo.geograph.domain.Root o2) {
            ((Job_Base.DO_State)o1.get$obj$state(true)).root = o2;
        }
        public dml.runtime.Role<it.algo.geograph.domain.Root,it.algo.geograph.domain.Job> getInverseRole() {
            return it.algo.geograph.domain.Root.role$$jobs;
        }
        
    };
    public static pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root> RootHasJobs = new pt.ist.fenixframework.pstm.LoggingRelation<it.algo.geograph.domain.Job,it.algo.geograph.domain.Root>(role$$root);
    static {
        it.algo.geograph.domain.Root.RootHasJobs = RootHasJobs.getInverseRelation();
    }
    
    static {
        RootHasJobs.setRelationName("it.algo.geograph.domain.Job.RootHasJobs");
    }
    
    
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Job_Base() {
        super();
    }
    
    public java.lang.String getName() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "name");
        return ((DO_State)this.get$obj$state(false)).name;
    }
    
    public void setName(java.lang.String name) {
        ((DO_State)this.get$obj$state(true)).name = name;
    }
    
    public boolean getEnabled() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "enabled");
        return ((DO_State)this.get$obj$state(false)).enabled;
    }
    
    public void setEnabled(boolean enabled) {
        ((DO_State)this.get$obj$state(true)).enabled = enabled;
    }
    
    public it.algo.geograph.domain.Root getRoot() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "root");
        return ((DO_State)this.get$obj$state(false)).root;
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
        private java.lang.String name;
        private boolean enabled;
        private it.algo.geograph.domain.Root root;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.name = this.name;
            newCasted.enabled = this.enabled;
            newCasted.root = this.root;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private java.lang.String name;
            private boolean enabled;
            private it.algo.geograph.domain.Root root;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.name = obj.name;
                this.enabled = obj.enabled;
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
                state.name = this.name;
                state.enabled = this.enabled;
                state.root = this.root;
                
            }
            
        }
        
    }
    
}
