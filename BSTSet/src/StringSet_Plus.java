/**
 * This is a String version of the java.util.Set
 * interface. It is simplified by removing the generic 
 * feature. This means that there is no need to cast 
 * the elements of the collection to java.lang.Comparable
 * type. This is the interface for the plus version of
 * the assignment. It adds three methods to the check 
 * version of the interface. The three methods each
 * return an iterator. They differ in the type of tree
 * traversal supported by the iterator.
 */
public interface StringSet_Plus extends StringSet_Check {
    
    /**
     * Returns an iterator over the elements in this set. 
     * The elements are returned using an in-order 
     * traversal of the underlying tree. 
     * 
     * @return an iterator over the elements in this set
     */
    public StringIterator iteratorInOrder();
    
    /**
     * Returns an iterator over the elements in this set. 
     * The elements are returned using a pre-order 
     * traversal of the underlying tree. 
     * 
     * @return an iterator over the elements in this set
     */
    public StringIterator iteratorPreOrder();
    
    /**
     * Returns an iterator over the elements in this set. 
     * The elements are returned using a post-order 
     * traversal of the underlying tree. 
     * 
     * @return an iterator over the elements in this set
     */
    public StringIterator iteratorPostOrder();
    
}
