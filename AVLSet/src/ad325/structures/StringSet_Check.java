package ad325.structures;
/**
 * This is a String version of the java.util.Set
 * interface. It is simplified by removing the generic 
 * feature. This means that there is no need to cast 
 * the elements of the collection to java.lang.Comparable
 * type. This is the interface for the check version of
 * the assignment. It adds the single method remove to
 * the minus version of the interface.
 */
public interface StringSet_Check extends StringSet_Minus {

    /**
     * Removes the specified element from this set if 
     * it is present. More formally, this removes an 
     * element e such that s.equals(e), if this set 
     * contains such an element. Returns true if this 
     * set contained the element (or equivalently, if 
     * this set changed as a result of the call). 
     * (This set will not contain the element once the 
     * call returns.)
     *
     * @param s the String to be removed from this set, 
     * if present
     * @return true if this set contained the specified 
     * element
     * @throws IllegalArgumentException if the specified 
     * element is null
     */
    public boolean remove(String s);

    /**
     * Returns the number of elements in this set (its 
     * cardinality).
     *
     * @return the number of elements in this set (its 
     * cardinality)
     */
    public int size();

}