/**
 * This is a String version of the java.util.Set
 * interface. It is simplified by removing the generic 
 * feature. This means that there is no need to cast 
 * the elements of the collection to java.lang.Comparable
 * type. This is the interface for the minus version of
 * the assignment.
 */
public interface StringSet_Minus {
    
    /**
     * Adds the specified element to this set if it is 
     * not already present. More formally, adds the 
     * specified element s to this set if the set contains 
     * no element s2 such that s.equals(s2). If this 
     * set already contains the element, the call leaves 
     * the set unchanged and returns false. This ensures 
     * that the set never contains duplicate elements.
     * 
     * @param s element to be added to this set
     * @return true if this set did not already contain 
     * the specified element
     * @throws NullPointerException if the specified 
     * element is null
     */
    public boolean add(String s);
    
    /**
     * Removes all of the elements from this set. The set 
     * will be empty after this call returns.
     */
    public void clear();
    
    /**
     * Returns true if this set contains the 
     * specified element. More formally, returns true 
     * if and only if this set contains an element e 
     * such that s.equals(e).
     * 
     * @param s element whose presence in this set is 
     * to be tested
     * @return true if this set contains the specified 
     * element
     * @throws NullPointerException if the specified 
     * element is null
     */
    public boolean contains(String s);
    
    /**
     * Returns true if this set contains no elements.
     * 
     * @return true if this set contains no elements
     */
    public boolean isEmpty();
    
    /**
     * Returns a String containing all of the elements 
     * in this set ordered using an in-order traversal
     * of the underlying tree. The values are separated 
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     * 
     * @return A string representation of the elements of
     * the set ordered by an in-order traversal of the 
     * underlying tree.
     */
    public String toStringInOrder();
    
    /**
     * Returns a String containing all of the elements 
     * in this set ordered using a pre-order traversal
     * of the underlying tree. The values are separated 
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     * 
     * @return A string representation of the elements of
     * the set ordered by a pre-order traversal of the 
     * underlying tree.
     */
    public String toStringPreOrder();
    
    /**
     * Returns a String containing all of the elements 
     * in this set ordered using a post-order traversal
     * of the underlying tree. The values are separated 
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     * 
     * @return A string representation of the elements of
     * the set ordered by a post-order traversal of the 
     * underlying tree.
     * 
     */
    public String toStringPostOrder();
    
}
