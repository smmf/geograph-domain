package org.cloudtm.framework.ispn.collections.bplustree;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class BPlusTree_Base extends org.cloudtm.framework.ispn.AbstractDomainObject {
    public static dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.BPlusTree,org.cloudtm.framework.ispn.collections.bplustree.AbstractNode> role$$root = new dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.BPlusTree,org.cloudtm.framework.ispn.collections.bplustree.AbstractNode>() {
        public void add(org.cloudtm.framework.ispn.collections.bplustree.BPlusTree o1, org.cloudtm.framework.ispn.collections.bplustree.AbstractNode o2, dml.runtime.Relation<org.cloudtm.framework.ispn.collections.bplustree.BPlusTree,org.cloudtm.framework.ispn.collections.bplustree.AbstractNode> relation) {
            if (o1 != null) {
                org.cloudtm.framework.ispn.collections.bplustree.AbstractNode old2 = o1.getRoot();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setRoot$unidirectional(o2);
                }
            }
        }
        public void remove(org.cloudtm.framework.ispn.collections.bplustree.BPlusTree o1, org.cloudtm.framework.ispn.collections.bplustree.AbstractNode o2) {
            if (o1 != null) {
                o1.setRoot$unidirectional(null);
            }
        }
        public dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.AbstractNode,org.cloudtm.framework.ispn.collections.bplustree.BPlusTree> getInverseRole() {
            return new dml.runtime.RoleEmpty<org.cloudtm.framework.ispn.collections.bplustree.AbstractNode,org.cloudtm.framework.ispn.collections.bplustree.BPlusTree>(this);
        }
        
    };
    public static dml.runtime.DirectRelation<org.cloudtm.framework.ispn.collections.bplustree.BPlusTree,org.cloudtm.framework.ispn.collections.bplustree.AbstractNode> BPlusTreeHasRootNode = new dml.runtime.DirectRelation<org.cloudtm.framework.ispn.collections.bplustree.BPlusTree,org.cloudtm.framework.ispn.collections.bplustree.AbstractNode>(role$$root);
    
    public  BPlusTree_Base() {
        
    }
    
    public org.cloudtm.framework.ispn.collections.bplustree.AbstractNode getRoot() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":root");
        return (oid == null || oid instanceof NullClass ? null : (org.cloudtm.framework.ispn.collections.bplustree.AbstractNode)fromOid((String)oid));
    }
    
    public void setRoot(org.cloudtm.framework.ispn.collections.bplustree.AbstractNode root) {
        BPlusTreeHasRootNode.add((org.cloudtm.framework.ispn.collections.bplustree.BPlusTree)this, root);
    }
    
    public boolean hasRoot() {
        return (getRoot() != null);
    }
    
    public void removeRoot() {
        setRoot(null);
    }
    
    public void setRoot$unidirectional(org.cloudtm.framework.ispn.collections.bplustree.AbstractNode root) {
        IspnTxManager.cachePut(getOid() + ":root", (root == null ? NULL_OBJECT : root.getOid()));
    }
    
}
