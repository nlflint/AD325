package ad325.data_structures;

/**
 * This is a simplified interface for a priority queue. This will create
 * a min heap for the underlying storage. This is for the minus version.
 * If you implement the generic PriorityQueue interface for the standard
 * or challenge options, use your generic PriorityQueue to provide an
 * implementation of this interface.
 */
public interface PriorityStringQueueInterface {
    
    /**
     * Inserts the given string into the queue with the given priority
     * @param s The string to be added
     * @param p The priority for this string
     * @return true (queue has been updated)
     * @throws IllegalArgumentException if the string is null or empty
     */
    public boolean add(String s, int p);
    
    /**
     * Retrieves the first item in the queue, but does not remove it
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    public String peek();

    /**
     * Empties the current priority queue.
     */
    public void clear();
    
    /**
     * Retrieves the first item in the queue and removes it
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    public String remove();
    
    /**
     * Retrieves the number of elements in the queue.
     * @return The number of elements in the queue
     */
    public int size();
    
    /**
     * Returns an array containing the elements in this queue.
     * The order of the elements is not specified. However, if the
     * array is a copy of the underlying storage for the priority
     * queue, this method could be very helpful in testing and
     * debugging the queue.
     * <p>
     * The array object returned by this method is independent of the
     * queue. That is, the caller may reorder or replace values within
     * the returned array without any impact on the priority queue.
     * @return An array containing the elements of this queue
     */
    public Object[] toArray();
    
}
