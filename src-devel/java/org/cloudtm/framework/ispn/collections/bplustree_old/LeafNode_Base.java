package org.cloudtm.framework.ispn.collections.bplustree;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class LeafNode_Base extends org.cloudtm.framework.ispn.collections.bplustree.AbstractNode {
    public static dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> role$$next = new dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode>() {
        public void add(org.cloudtm.framework.ispn.collections.bplustree.LeafNode o1, org.cloudtm.framework.ispn.collections.bplustree.LeafNode o2, dml.runtime.Relation<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> relation) {
            if (o1 != null) {
                org.cloudtm.framework.ispn.collections.bplustree.LeafNode old2 = o1.getNext();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setNext$unidirectional(o2);
                }
            }
        }
        public void remove(org.cloudtm.framework.ispn.collections.bplustree.LeafNode o1, org.cloudtm.framework.ispn.collections.bplustree.LeafNode o2) {
            if (o1 != null) {
                o1.setNext$unidirectional(null);
            }
        }
        public dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> getInverseRole() {
            return org.cloudtm.framework.ispn.collections.bplustree.LeafNode.role$$previous;
        }
        
    };
    public static dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> role$$previous = new dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode>() {
        public void add(org.cloudtm.framework.ispn.collections.bplustree.LeafNode o1, org.cloudtm.framework.ispn.collections.bplustree.LeafNode o2, dml.runtime.Relation<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> relation) {
            if (o1 != null) {
                org.cloudtm.framework.ispn.collections.bplustree.LeafNode old2 = o1.getPrevious();
                if (o2 != old2) {
                    relation.remove(o1, old2);
                    o1.setPrevious$unidirectional(o2);
                }
            }
        }
        public void remove(org.cloudtm.framework.ispn.collections.bplustree.LeafNode o1, org.cloudtm.framework.ispn.collections.bplustree.LeafNode o2) {
            if (o1 != null) {
                o1.setPrevious$unidirectional(null);
            }
        }
        public dml.runtime.Role<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> getInverseRole() {
            return org.cloudtm.framework.ispn.collections.bplustree.LeafNode.role$$next;
        }
        
    };
    public static dml.runtime.Relation<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> LeafNodeHasSibling$Inverse;
    public static dml.runtime.DirectRelation<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode> LeafNodeHasSibling = new dml.runtime.DirectRelation<org.cloudtm.framework.ispn.collections.bplustree.LeafNode,org.cloudtm.framework.ispn.collections.bplustree.LeafNode>(role$$previous);
    static {
        org.cloudtm.framework.ispn.collections.bplustree.LeafNode.LeafNodeHasSibling$Inverse = LeafNodeHasSibling.getInverseRelation();
    }
    
    public  LeafNode_Base() {
        
    }
    
    public java.util.TreeMap<String,org.cloudtm.framework.ispn.AbstractDomainObject> getEntries() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":entries");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.util.TreeMap<String,org.cloudtm.framework.ispn.AbstractDomainObject>)obj;
    }
    
    public void setEntries(java.util.TreeMap<String,org.cloudtm.framework.ispn.AbstractDomainObject> entries) {
        IspnTxManager.cachePut(getOid() + ":entries", (entries == null ? NULL_OBJECT : entries));
    }
    
    public org.cloudtm.framework.ispn.collections.bplustree.LeafNode getNext() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":next");
        return (oid == null || oid instanceof NullClass ? null : (org.cloudtm.framework.ispn.collections.bplustree.LeafNode)fromOid((String)oid));
    }
    
    public void setNext(org.cloudtm.framework.ispn.collections.bplustree.LeafNode next) {
        LeafNodeHasSibling$Inverse.add((org.cloudtm.framework.ispn.collections.bplustree.LeafNode)this, next);
    }
    
    public boolean hasNext() {
        return (getNext() != null);
    }
    
    public void removeNext() {
        setNext(null);
    }
    
    public void setNext$unidirectional(org.cloudtm.framework.ispn.collections.bplustree.LeafNode next) {
        IspnTxManager.cachePut(getOid() + ":next", (next == null ? NULL_OBJECT : next.getOid()));
    }
    
    public org.cloudtm.framework.ispn.collections.bplustree.LeafNode getPrevious() {
        Object oid = IspnTxManager.cacheGet(getOid() + ":previous");
        return (oid == null || oid instanceof NullClass ? null : (org.cloudtm.framework.ispn.collections.bplustree.LeafNode)fromOid((String)oid));
    }
    
    public void setPrevious(org.cloudtm.framework.ispn.collections.bplustree.LeafNode previous) {
        LeafNodeHasSibling.add((org.cloudtm.framework.ispn.collections.bplustree.LeafNode)this, previous);
    }
    
    public boolean hasPrevious() {
        return (getPrevious() != null);
    }
    
    public void removePrevious() {
        setPrevious(null);
    }
    
    public void setPrevious$unidirectional(org.cloudtm.framework.ispn.collections.bplustree.LeafNode previous) {
        IspnTxManager.cachePut(getOid() + ":previous", (previous == null ? NULL_OBJECT : previous.getOid()));
    }
    
}
