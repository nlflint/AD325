package ad325.data_structures;

import java.util.Arrays;

/**
 * This is a priority queue, implemented using a heap.
 *
 * @Author Nathan Flint
 * Assignment: 4
 */
public class PrioritizedWords implements PriorityStringQueueInterface {
    private PriorityString[] values;
    private int nextEmptyIndex;
    private int nAry;
    private int nAryMinusOne;

    public PrioritizedWords() {
        values = new PriorityString[10];
        nextEmptyIndex = 0;
        nAry = 4;
        nAryMinusOne = nAry - 1;
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
        if (s == null || s.equals(""))
            throw new IllegalArgumentException("s argument is null or empty. Cannot add a null or empty string.");

        if (arrayIsFull())
            doubleArraySpace();

        int newIndex = nextEmptyIndex++;

        values[newIndex] = new PriorityString(s, p);

        while (hasGreaterPriorityThanParent(newIndex)) {
            int parentIndex = getParentIndex(newIndex);
            swapValues(newIndex, parentIndex);
            newIndex = parentIndex;
        }

        return true;
    }

    // Checks if priority of given index is higher than it's parent index.
    private boolean hasGreaterPriorityThanParent(int newIndex) {
        return hasGreaterPriority(newIndex, getParentIndex(newIndex));
    }

    // Tests if the priority at the first index is greater than the second
    private boolean hasGreaterPriority(int first, int second) {
        if (second < 0)
            return false;

        int firstPriority = values[first].priority;
        int secondPriority = values[second].priority;

        return firstPriority < secondPriority;
    }

    // Gets parent index given the child index
    private int getParentIndex(int childIndex) {
        int parentIndex = (childIndex + nAryMinusOne) / nAry - 1;
        return parentIndex;
    }

    // Doubles available space in the heap
    private void doubleArraySpace() {
        values = Arrays.copyOf(values, values.length * 2);
    }

    // Test if the heap has reach capacity
    private boolean arrayIsFull() {
        return nextEmptyIndex == values.length;

    }

    // swaps two elements at the given indexes, and returns the second index.
    private int swapValues(int firstIndex, int secondIndex) {
        PriorityString temp = values[firstIndex];
        values[firstIndex] = values[secondIndex];
        values[secondIndex] = temp;

        return secondIndex;
    }

    /**
     * Retrieves the first item in the queue, but does not remove it
     *
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public String peek() {
        if (size() < 1)
            throw new IllegalStateException("Queue is empty. Cannot peek and empty queue.");
        return values[0].value;
    }

    /**
     * Empties the current priority queue.
     */
    @Override
    public void clear() {
        nextEmptyIndex = 0;
    }

    /**
     * Retrieves the first item in the queue and removes it
     *
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public String remove() {
        if (size() < 1)
            throw new IllegalStateException("Queue is empty. Cannot remove something from an empty queue.");

        int workingIndex = 0;
        String removedValue = values[workingIndex].value;
        moveLastValueToRoot();

        while (hasAChildWithGreaterPriority(workingIndex)) {
            int childIndex = getIndexOfChildWithHighestPriority(workingIndex);
            swapValues(workingIndex, childIndex);
            workingIndex = childIndex;
        }

        return removedValue;
    }

    // Searches children of the given index and returns the child's index with the greatest priority
    private int getIndexOfChildWithHighestPriority(int index) {
        int[] children = getChildIndexes(index);

        int greatestChild = children[0];
        int greatestPriority = values[children[0]].priority;

        for (int child : children) {
            if (!indexExists(child))
                return greatestChild;

            int childPriority = values[child].priority;
            if (childPriority < greatestPriority) {
                greatestChild = child;
                greatestPriority = childPriority;
            }

        }
        return greatestChild;
    }

    // builds an array of children indexes, given the parent index.
    private int[] getChildIndexes(int parent) {
        int[] children = new int[nAry];

        for (int i = 0; i < nAry; i++)
            children[i] = (parent + 1) * nAry - nAryMinusOne + i;

        return children;
    }

    // Moves last item on the heap, to the root element.
    private void moveLastValueToRoot() {
        nextEmptyIndex--;
        values[0] = values[nextEmptyIndex];
    }

    // Tests if the given parent has a child of priority greater than itself.
    private boolean hasAChildWithGreaterPriority(int parent) {
        int parentPriority = values[parent].priority;
        int[] children = getChildIndexes(parent);

        for (int child : children) {
            if (!indexExists(child))
                return false;

            int childPriority = values[child].priority;
            if (childPriority < parentPriority)
                return true;
        }
        return false;
    }

    // Tests if the given index exists in the heap
    private boolean indexExists(int index) {
        return index < nextEmptyIndex;
    }

    /**
     * Retrieves the number of elements in the queue.
     *
     * @return The number of elements in the queue
     */
    @Override
    public int size() {
        return nextEmptyIndex;
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
        String[] strings = new String[nextEmptyIndex];
        for (int i = 0; i < nextEmptyIndex; i++) {
            strings[i] = values[i].value;
        }
        return strings;
    }
}

class PriorityString {
    final int priority;
    final String value;

    public PriorityString(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }
}
