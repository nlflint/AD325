/**
 * This is a String version of the java.util.Iterator
 * interface. It is simplified by removing the generic 
 * feature. This means that there is no need to cast the
 * elements of the collection to java.lang.Comparable.
 */
public interface StringIterator {
    
    /**
     * Returns true if the iteration has more elements. 
     * (In other words, returns true if next() would 
     * return an element rather than throwing an 
     * exception.)
     * 
     * @return true if the iteration has more elements
     */
    public boolean hasNext();
    
    /**
     * Returns the next element in the iteration.
     * 
     * @return the next element in the iteration
     * @throws IllegalStateException if the iteration 
     * has no more elements
     */
    public String next();
    
}
