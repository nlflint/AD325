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
    
    /**
     * Removes from the underlying collection the last 
     * element returned by this iterator (optional 
     * operation). This method can be called only once 
     * per call to next(). The behavior of an iterator 
     * is unspecified if the underlying collection is 
     * modified while the iteration is in progress in 
     * any way other than by calling this method.
     * 
     * @throws UnsupportedOperationException if the 
     * remove operation is not supported by this 
     * iterator
     */
    public void remove();
    
}
