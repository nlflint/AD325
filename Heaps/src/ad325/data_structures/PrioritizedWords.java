package ad325.data_structures;

import java.util.Arrays;

/**
 * Created by nate on 2/6/16.
 */
public class PrioritizedWords implements PriorityStringQueueInterface {
    private StringPriority[] values;
    private int nextEmptyIndex;
    private int nextIndexToRemove;
    private int size;

    public PrioritizedWords() {
        values = new StringPriority[10];
        nextEmptyIndex = 0;
        nextIndexToRemove = -1;
        size = 0;
    }
    /**
     * Inserts the given string into the queue with the given priority
     *
     * @param s The string to be added
     * @param p The priority for this string
     * @return true (queue has been updated)
     * @throws IllegalArgumentException if the string is null or empty
     */
    @Override
    public boolean add(String s, int p) {
        if (arrayIsFull())
            doubleArraySpace();

        values[nextEmptyIndex] = new StringPriority(s, p);

        int currentIndex = nextEmptyIndex;
        int nextIndex = nextEmptyIndex - 1;

        while(nextIndex > -1 && values[currentIndex].priority < values[nextIndex].priority) {
            swapValues(currentIndex, nextIndex);
            nextIndex--;
            currentIndex--;
        }
        nextEmptyIndex++;
        size++;
        return false;
    }

    private void doubleArraySpace() {
        values = Arrays.copyOf(values, values.length * 2);
    }

    private boolean arrayIsFull() {
        return nextEmptyIndex == values.length;

    }

    private void swapValues(int firstIndex, int secondIndex) {
        StringPriority temp = values[firstIndex];
        values[firstIndex] = values[secondIndex];
        values[secondIndex] = temp;
    }

    /**
     * Retrieves the first item in the queue, but does not remove it
     *
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public String peek() {
        return null;
    }

    /**
     * Empties the current priority queue.
     */
    @Override
    public void clear() {

    }

    /**
     * Retrieves the first item in the queue and removes it
     *
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public String remove() {
        nextIndexToRemove++;
        size--;
        return values[nextIndexToRemove].value;
    }

    /**
     * Retrieves the number of elements in the queue.
     *
     * @return The number of elements in the queue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an array containing the elements in this queue.
     * The order of the elements is not specified. However, if the
     * array is a copy of the underlying storage for the priority
     * queue, this method could be very helpful in testing and
     * debugging the queue.
     * <p/>
     * The array object returned by this method is independent of the
     * queue. That is, the caller may reorder or replace values within
     * the returned array without any impact on the priority queue.
     *
     * @return An array containing the elements of this queue
     */
    @Override
    public Object[] toArray() {
        return new Object[0];
    }
}

class StringPriority {
    final int priority;
    final String value;

    public StringPriority(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }
}
