package ad325.data_structures;

import java.util.Arrays;

/**
 * Created by nate on 2/6/16.
 */
public class PrioritizedWords implements PriorityStringQueueInterface {
    private StringPriority[] values;
    private int nextEmptyIndex;

    public PrioritizedWords() {
        values = new StringPriority[10];
        nextEmptyIndex = 0;
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

        int newIndex = nextEmptyIndex++;

        values[newIndex] = new StringPriority(s, p);

        while (hasGreaterPriorityThanParent(newIndex)) {
            int parentIndex = getParentIndex(newIndex);
            swapValues(newIndex, parentIndex);
            newIndex = parentIndex;
        }

        return false;
    }

    private boolean hasGreaterPriorityThanParent(int newIndex) {
        return hasGreaterPriority(newIndex, getParentIndex(newIndex));
    }

    private boolean hasGreaterPriority(int first, int second) {
        if (second < 0)
            return false;

        int firstPriority = values[first].priority;
        int secondPriority = values[second].priority;

        return firstPriority < secondPriority;
    }

    private int getParentIndex(int childIndex) {
        int parentIndex = (childIndex + 1) / 2 - 1;
        return parentIndex;
    }

    private void doubleArraySpace() {
        values = Arrays.copyOf(values, values.length * 2);
    }

    private boolean arrayIsFull() {
        return nextEmptyIndex == values.length;

    }

    private int swapValues(int firstIndex, int secondIndex) {
        StringPriority temp = values[firstIndex];
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

    private int getIndexOfChildWithHighestPriority(int index) {
        int[] children = getChildIndexes(index);

        int greatestChild = children[0];
        int greatestPriority = values[children[0]].priority;

        for (int child : children) {
            int childPriority = values[child].priority;
            if (childPriority < greatestPriority)
                greatestChild = child;
        }
        return greatestChild;
    }

    private int[] getChildIndexes(int index) {
        int[] children = new int[2];

        for (int i = 0; i < 2; i++)
            children[i] = (index + 1) * 2 - 1 + i;

        return children;
    }

    private void moveLastValueToRoot() {
        nextEmptyIndex--;
        values[0] = values[nextEmptyIndex];
    }

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
