package ad325.data_structures;

/**
 * This is a simplified interface for a priority queue. This is more
 * general than the PriorityStringQueue interface. The can hold any
 * reference type. The implementation should support using either a
 * min heap or a max heap as the underlying storage. See the write-up
 * for more details.
 */
public interface PriorityQueueInterface<E> {
    
    /**
     * Inserts the given element into the priority queue.
     * @param e The element to be added
     * @return true (queue has been updated)
     * @throws IllegalArgumentException if the element is null
     * or cannot be cast to a type usable by this queue
     */
    public boolean add(E e);
    
    /**
     * Retrieves the first item in the queue, but does not remove it
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    public E peek();

    /**
     * Empties the current priority queue.
     */
    public void clear();
    
    /**
     * Retrieves the first item in the queue and removes it
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    public E remove();
    
    /**
     * Retrieves the number of elements in the queue.
     * @return The number of elements in the queue
     */
    public int size();
    
    /**
     * Returns an array containing the elements in this queue.
     * The order of the elements is not specified. However, if the
     * array is a copy of the underlying storage for the priority
     * queue, this method could be very helpful in testing the queue.
     * <p>
     * The array object returned by this method is independent of the
     * queue. That is, the caller may reorder or replace values within
     * the returned array without any impact on the priority queue.
     * @return An array containing the elements of this queue
     */
    public Object[] toArray();
    
}
