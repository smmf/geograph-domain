package org.cloudtm.framework.ispn.collections.bplustree;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class AbstractNode_Base extends org.cloudtm.framework.ispn.AbstractDomainObject {
    public static dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.AbstractNode,org.cloudtm.framework.ispn.collections.bplustree.InnerNode> role$$parent = new dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.AbstractNode,org.cloudtm.framework.ispn.collections.bplustree.InnerNode>() {
        public void add(org.cloudtm.framework.ispn.collections.bplustree.AbstractNode o1, org.cloudtm.framework.ispn.collections.bplustree.InnerNode o2, dml.runtime.Relation<org.cloudtm.framework.ispn.collections.bplustree.AbstractNode,org.cloudtm.framework.ispn.collections.bplustree.InnerNode> relation) {
            if (o1 != null) {
                org.cloudtm.framework.ispn.collections.bplustree.InnerNode old2 = o1.getParent();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setParent$unidirectional(o2);
                }
            }
        }
        public void remove(org.cloudtm.framework.ispn.collections.bplustree.AbstractNode o1, org.cloudtm.framework.ispn.collections.bplustree.InnerNode o2) {
            if (o1 != null) {
                o1.setParent$unidirectional(null);
            }
        }
        public dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.InnerNode,org.cloudtm.framework.ispn.collections.bplustree.AbstractNode> getInverseRole() {
            return new dml.runtime.RoleEmpty<org.cloudtm.framework.ispn.collections.bplustree.InnerNode,org.cloudtm.framework.ispn.collections.bplustree.AbstractNode>(this);
        }
        
    };
    public static dml.runtime.DirectRelation<org.cloudtm.framework.ispn.collections.bplustree.AbstractNode,org.cloudtm.framework.ispn.collections.bplustree.InnerNode> NodeHasParent = new dml.runtime.DirectRelation<org.cloudtm.framework.ispn.collections.bplustree.AbstractNode,org.cloudtm.framework.ispn.collections.bplustree.InnerNode>(role$$parent);
    
    public  AbstractNode_Base() {
        
    }
    
    public org.cloudtm.framework.ispn.collections.bplustree.InnerNode getParent() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":parent");
        return (oid == null || oid instanceof NullClass ? null : (org.cloudtm.framework.ispn.collections.bplustree.InnerNode)fromOid((String)oid));
    }
    
    public void setParent(org.cloudtm.framework.ispn.collections.bplustree.InnerNode parent) {
        NodeHasParent.add((org.cloudtm.framework.ispn.collections.bplustree.AbstractNode)this, parent);
    }
    
    public boolean hasParent() {
        return (getParent() != null);
    }
    
    public void removeParent() {
        setParent(null);
    }
    
    public void setParent$unidirectional(org.cloudtm.framework.ispn.collections.bplustree.InnerNode parent) {
        IspnTxManager.cachePut(getOid() + ":parent", (parent == null ? NULL_OBJECT : parent.getOid()));
    }
    
}
