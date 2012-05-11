package org.cloudtm.framework.ispn.collections.bplustree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cloudtm.framework.ispn.AbstractDomainObject;

import static org.cloudtm.framework.ispn.collections.bplustree.BPlusTree.COMPARATOR_SUPPORTING_LAST_KEY;

public class LeafNode extends LeafNode_Base {
    
	//Pedro -- added by me
	public static LeafNode createNewLeafNode() {
		return new LeafNode(new TreeMap<String,AbstractDomainObject>(BPlusTree.COMPARATOR_SUPPORTING_LAST_KEY));
	}
	
    public LeafNode() {
	//Pedro -- i need an empty constructor for JGroups serialization
	//setEntries(new TreeMap<String,AbstractDomainObject>(BPlusTree.COMPARATOR_SUPPORTING_LAST_KEY));
    }

    private LeafNode(TreeMap<String,AbstractDomainObject> entries) {
	saveThis();
	setEntries(entries);
    }

    private TreeMap<String,AbstractDomainObject> replacePreviousMap(LeafNode leafNode) {
	TreeMap<String,AbstractDomainObject> newMap = new TreeMap<String,AbstractDomainObject>(leafNode.getEntries());
	leafNode.setEntries(newMap);
	return newMap;
    }

    public AbstractNode insert(String key, AbstractDomainObject value) {
	TreeMap<String,AbstractDomainObject> localMap = justInsert(key, value);

	if (localMap.size() <= BPlusTree.MAX_NUMBER_OF_ELEMENTS) { // it still fits :-)
	    return getRoot();
	} else { // must split this node
	    // find middle position
	    String keyToSplit = findRightMiddlePosition(localMap.keySet());

	    // split node in two
	    LeafNode leftNode = new LeafNode(new TreeMap<String,AbstractDomainObject>(localMap.headMap(keyToSplit)));
	    LeafNode rightNode = new LeafNode(new TreeMap<String,AbstractDomainObject>(localMap.tailMap(keyToSplit)));
	    fixLeafNodesListAfterSplit(leftNode, rightNode);

	    // propagate split to parent
	    if (getParent() == null) {  // make new root node
		InnerNode newRoot = new InnerNode(leftNode, rightNode, keyToSplit);
		return newRoot;
	    } else {
		// leftNode.parent = getParent();
		// rightNode.parent = getParent();
		return getParent().rebase(leftNode, rightNode, keyToSplit);
	    }
	}
    }
    
    private String findRightMiddlePosition(Collection<String> keys) {
	Iterator<String> keysIterator = keys.iterator();

	for (int i = 0; i < BPlusTree.LOWER_BOUND + 1; i++) {
	    keysIterator.next();
	}
	return keysIterator.next();
    }

    private TreeMap<String,AbstractDomainObject> justInsert(String key, AbstractDomainObject value) {
	TreeMap<String,AbstractDomainObject> localEntries = this.getEntries();

	// this test is performed because we need to return a new structure in
	// case an update occurs.  Objects inside VBoxes must be immutable.
	AbstractDomainObject currentValue = localEntries.get(key);
	if (currentValue == value && localEntries.containsKey(key)) {
	    return localEntries;
	} else {
	    TreeMap<String,AbstractDomainObject> newMap = replacePreviousMap(this);
	    newMap.put(key, value);
	    return newMap;
	}
    }

    private void fixLeafNodesListAfterSplit(LeafNode leftNode, LeafNode rightNode) {
	leftNode.setPrevious(this.getPrevious());
	rightNode.setNext(this.getNext());
	leftNode.setNext(rightNode);
    }

    public AbstractNode remove(String key) {
	TreeMap<String,AbstractDomainObject> localMap = justRemove(key);

	if (getParent() == null) {
	    return this;
	} else {
	    // if the removed key was the first we need to replace it in some parent's index
	    String replacementKey = getReplacementKeyIfNeeded(key);

	    if (localMap.size() < BPlusTree.LOWER_BOUND) {
		return getParent().underflowFromLeaf(key, replacementKey);
	    } else if (replacementKey != null) {
		return getParent().replaceDeletedKey(key, replacementKey);
	    } else {
		return getParent().getRoot();  // maybe a tiny faster than just getRoot() ?!
	    }
	}
    }

    private TreeMap<String,AbstractDomainObject> justRemove(String key) {
	TreeMap<String,AbstractDomainObject> localEntries = this.getEntries();

	// this test is performed because we need to return a new structure in
	// case an update occurs.  Objects inside VBoxes must be immutable.
	if (!localEntries.containsKey(key)) {
	    return localEntries;
	} else {
	    TreeMap<String,AbstractDomainObject> newMap = replacePreviousMap(this);
	    newMap.remove(key);
	    return newMap;
	}
    }

    // This method assumes that there is at least one more key (which is
    // always true if this is not the root node)
    private String getReplacementKeyIfNeeded(String deletedKey) {
	String firstKey = this.getEntries().firstKey();
	if (COMPARATOR_SUPPORTING_LAST_KEY.compare(deletedKey, firstKey) < 0) {
	    return firstKey;
	} else {
	    return null; // null means that key does not need replacement
	}
    }

    Map.Entry<String,AbstractDomainObject> removeBiggestKeyValue() {
	TreeMap<String,AbstractDomainObject> newMap = replacePreviousMap(this);
	Map.Entry<String,AbstractDomainObject> lastEntry = newMap.pollLastEntry();
	return lastEntry;
    }

    Map.Entry<String,AbstractDomainObject> removeSmallestKeyValue() {
	TreeMap<String,AbstractDomainObject> newMap = replacePreviousMap(this);
	Map.Entry<String,AbstractDomainObject> firstEntry = newMap.pollFirstEntry();
	return firstEntry;
    }

    String getSmallestKey() {
	return this.getEntries().firstKey();
    }

    void addKeyValue(Map.Entry keyValue) {
	TreeMap<String,AbstractDomainObject> newMap = replacePreviousMap(this);
	newMap.put((String)keyValue.getKey(), (AbstractDomainObject)keyValue.getValue());
    }

    void mergeWithLeftNode(AbstractNode leftNode, String splitKey) {
	LeafNode left = (LeafNode)leftNode; // this node does not know how to merge with another kind
	
	TreeMap<String,AbstractDomainObject> newMap = replacePreviousMap(this);
	newMap.putAll(left.getEntries());

	LeafNode nodeBefore = left.getPrevious();

	this.setPrevious(nodeBefore);
	if (nodeBefore != null) {
	    nodeBefore.setNext(this);
	}

	// no need to update parents, because they are always the same for the two merging leaf nodes
	assert(this.getParent() == leftNode.getParent());
    }

    public AbstractDomainObject get(String key) {
	return this.getEntries().get(key);
    }

    public AbstractDomainObject getIndex(int index) {
	if (index < 0) {
	    throw new IndexOutOfBoundsException();
	}

	if (index < shallowSize()) { // the required position is here
    	    Iterator<AbstractDomainObject> values = this.getEntries().values().iterator();
    	    for (int i = 0; i < index; i++) {
    	    	values.next();
    	    }
	    return values.next();
	} else {
	    LeafNode next = this.getNext();
	    if (next == null) {
		throw new IndexOutOfBoundsException();
	    }
	    return next.getIndex(index - shallowSize());
	}
    }

    public AbstractNode removeIndex(int index) {
	if (index < 0) {
	    throw new IndexOutOfBoundsException();
	}

	if (index < shallowSize()) { // the required position is here
	    Iterator<String> keys = this.getEntries().keySet().iterator();
	    for (int i = 0; i < index; i++) {
		keys.next();
	    }
	    return this.remove(keys.next());
	} else {
	    LeafNode next = this.getNext();
	    if (next == null) {
		throw new IndexOutOfBoundsException();
	    }
	    return next.removeIndex(index - shallowSize());
	}
    }

    public boolean containsKey(String key) {
	return this.getEntries().containsKey(key);
    }

    int shallowSize() {
	return this.getEntries().size();
    }

    public int size() {
	return this.getEntries().size();
    }

    public Iterator<AbstractDomainObject> iterator() {
	return new LeafNodeIterator(this);
    }

    private class LeafNodeIterator implements Iterator<AbstractDomainObject> {
	private Iterator<AbstractDomainObject> iterator;
	private LeafNode current;
	

	LeafNodeIterator(LeafNode leafNode) {
	    this.iterator = leafNode.getEntries().values().iterator();
	    this.current = leafNode;
	}

	public boolean hasNext() {
	    if (this.iterator.hasNext()) {
		return true;
	    } else {
		return this.current.getNext() != null;
	    }
	}

        public AbstractDomainObject next() {
	    if (!this.iterator.hasNext()) {
		LeafNode nextNode = this.current.getNext();
		if (nextNode != null) {
		    this.current = nextNode;
		    this.iterator = this.current.getEntries().values().iterator();
		} else {
		    throw new NoSuchElementException();
		}
	    }
	    return this.iterator.next();
	}

        public void remove() {
	    throw new UnsupportedOperationException("This implementation does not allow element removal via the iterator");
	}

    }

    public String dump(int level, boolean dumpKeysOnly, boolean dumpNodeIds) {
	StringBuilder str = new StringBuilder();
	str.append(BPlusTree.spaces(level));
	if (dumpNodeIds) {
	    str.append(this.getPrevious() + "<-[" + this + ": ");
	} else {
	    str.append("[: ");
	}

	for (Map.Entry<String, AbstractDomainObject> entry : this.getEntries().entrySet()) {
	    String key = entry.getKey();
	    AbstractDomainObject value = entry.getValue();
	    str.append("(" + key);
	    str.append(dumpKeysOnly ? ") " : "," + value.getOid() + ") ");
	}
	if (dumpNodeIds) {
	    str.append("]->" + this.getNext() + " ^" + getParent() + "\n");
	} else {
	    str.append("]\n");
	}

	return str.toString();
    }

}