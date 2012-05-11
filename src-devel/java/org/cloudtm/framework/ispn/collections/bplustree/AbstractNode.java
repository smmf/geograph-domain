package org.cloudtm.framework.ispn.collections.bplustree;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.TreeMap;

import org.cloudtm.framework.ispn.AbstractDomainObject;
// import pt.ist.fenixframework.Externalization;
// import pt.ist.fenixframework.pstm.AbstractDomainObject;

/** The keys comparison function should be consistent with equals. */
public abstract class AbstractNode<T extends AbstractDomainObject> extends AbstractNode_Base implements Iterable {
    /* Node Interface */

    /** Inserts the given key-value pair and returns the (possibly new) root node */
    abstract AbstractNode insert(String key, T value);
    /** Removes the element with the given key */
    abstract AbstractNode remove(String key);
    /** Returns the value to which the specified key is mapped, or <code>null</code> if this map contains no mapping for the key. */
    abstract T get(String key);
    /** Returns the value at the given index 
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size()) */
    abstract T getIndex(int index);
    /** Returns the value that was removed from  the given index 
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size()) */
    abstract AbstractNode removeIndex(int index);
    /** Returns <code>true</code> if this map contains a mapping for the specified key.  */
    abstract boolean containsKey(String key);
    /** Returns the number os key-value mappings in this map */
    abstract int size();

    abstract String dump(int level, boolean dumpKeysOnly, boolean dumpNodeIds);


    /* **** Uncomment the following to support pretty printing of nodes **** */
    
    // static final AtomicInteger GLOBAL_COUNTER = new AtomicInteger(0);
    // protected int counter = GLOBAL_COUNTER.getAndIncrement();
    // public String toString() {
    // 	return "" + counter;
    // }

    /* *********** */

    
    public  AbstractNode() {
        super();
        //pt.ist.fenixframework.example.tpcw.ispn.IspnTxManager.staticSave(this);
    }
	
	protected void saveThis() {
		org.cloudtm.framework.ispn.IspnTxManager.staticSave(this);
	}

    AbstractNode getRoot() {
	InnerNode thisParent = this.getParent();
	return thisParent == null ? this : thisParent.getRoot();
    }

    abstract Map.Entry<String,T> removeBiggestKeyValue();
    abstract Map.Entry<String,T> removeSmallestKeyValue();
    abstract String getSmallestKey();
    abstract void addKeyValue(Map.Entry keyValue);
    // merge elements from the left node into this node. smf: maybe LeafNode can be a subclass of InnerNode
    abstract void mergeWithLeftNode(AbstractNode leftNode, String splitKey);
    // the number of _elements_ in this node (not counting sub-nodes)
    abstract int shallowSize();

    // smf: this will be necessary when supporting custom value types (in this case, TreeMap as a value-type)
    // public static byte[] externalizeTreeMap(TreeMap treeMap) {
    //     return Externalization.externalizeObject(new TreeMapExternalization(treeMap));
    // }

    // public static TreeMap internalizeTreeMap(byte[] externalizedTreeMap) {
    //     TreeMapExternalization treeMapExternalization = Externalization.internalizeObject(externalizedTreeMap);

    //     return treeMapExternalization.toTreeMap();
    // }

    private static class TreeMapExternalization implements Serializable {
	private static final long serialVersionUID = 1L;

	private String[] keyOids;
	private String[] valueOids; // smf: this should be changed to contains the objects themselves (maybe)

	TreeMapExternalization(TreeMap treeMap) {
	    int size = treeMap.size();
	    this.keyOids = new String[size];
	    this.valueOids = new String[size];

	    int i = 0;
	    for (Map.Entry entry : (java.util.Set<Map.Entry>)treeMap.entrySet()) {
		this.keyOids[i] = (String)entry.getKey();
		Object value = entry.getValue();
		this.valueOids[i] = (value == null ? "" : ((AbstractDomainObject)value).getOid());
		i++;
	    }
	}

	TreeMap toTreeMap() {
	    TreeMap treeMap = new TreeMap(BPlusTree.COMPARATOR_SUPPORTING_LAST_KEY);

	    for (int i = 0; i < this.keyOids.length; i++) {
		String value = this.keyOids[i];
		treeMap.put(this.keyOids[i], (value == "" ? null : AbstractDomainObject.fromOid(this.valueOids[i])));
	    }
	    return treeMap;
	}
    }
}
