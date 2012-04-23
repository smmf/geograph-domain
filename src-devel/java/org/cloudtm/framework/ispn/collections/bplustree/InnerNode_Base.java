package org.cloudtm.framework.ispn.collections.bplustree;

import org.cloudtm.framework.ispn.IspnTxManager;
import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;

public abstract class InnerNode_Base extends org.cloudtm.framework.ispn.collections.bplustree.AbstractNode {
    
    public  InnerNode_Base() {
        
    }
    
    public java.util.TreeMap<String,AbstractNode> getSubNodes() {
        Object obj = IspnTxManager.cacheGet(getOid() + ":subNodes");
        if (obj == null || obj instanceof NullClass) return null;
        return (java.util.TreeMap<String,AbstractNode>)obj;
    }
    
    public void setSubNodes(java.util.TreeMap<String,AbstractNode> subNodes) {
        IspnTxManager.cachePut(getOid() + ":subNodes", (subNodes == null ? NULL_OBJECT : subNodes));
    }
    
}
