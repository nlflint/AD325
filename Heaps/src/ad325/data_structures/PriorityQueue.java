package ad325.data_structures;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This is a generic priority queue, implemented using a heap.
 *
 * @Author Nathan Flint
 * Assignment: 4
 * Level: Extra Plus
 */
public class PriorityQueue<E>
        implements PriorityQueueInterface<E>, ComparatorConstructorImplemented, NaryTreeConstructorImplemented {
    // number of elements in the queue and also the next empty index of the heap
    private int nextEmptyIndex;
    // Comparator used for determining priority of the elements
    private final Comparator<E> comparator;
    // a Heap containing the elements of the queue.
    private E[] values;
    // the nAry of the heap
    private final int nAry;
    // utility value used for parent/child index conversion
    private final int nAryMinusOne;

    /**
     * Constructor. Defaults to 4-ary heap.
     * @param comparator the comparator that will be used to implement min or max heap.
     * @throws IllegalArgumentException if nAry is NOT between 2 and 16 inclusive
     */
    public PriorityQueue(Comparator<E> comparator) {
        this(comparator, 4);
    }

    /**
     * Constructor.
     * @param comparator the comparator that will be used to implement min or max heap.
     * @param nAry the nary of the underlying heap
     * @throws IllegalArgumentException if nAry is NOT between 2 and 16 inclusive
     */

    public PriorityQueue(Comparator<E> comparator, int nAry) {
        if (nAry <  2 || nAry > 16)
            throw new IllegalArgumentException("nAry must be between 2 and 16, inclusive.");

        this.comparator = comparator;
        nextEmptyIndex = 0;
        values = buildInitialArray();
        this.nAry = nAry;
        nAryMinusOne = nAry - 1;
    }

    // Builds the initial array. Isolated to it's own function because of suppression.
    @SuppressWarnings("unchecked")
    private E[] buildInitialArray() {
        return (E[]) new Object[10];
    }

    /**
     * Inserts the given element into the priority queue.
     *
     * @param e The element to be added
     * @return true (queue has been updated)
     * @throws IllegalArgumentException if the element is null
     *                                  or cannot be cast to a type usable by this queue
     */
    @Override
    public boolean add(E e) {
        if (e == null)
            throw new IllegalArgumentException("e argument is null. Cannot add null thing to queue.");

        if (arrayIsFull())
            doubleArraySpace();

        int newIndex = nextEmptyIndex++;

        values[newIndex] = e;

        while (hasHigherPriorityThanParent(newIndex)) {
            int parentIndex = getParentIndex(newIndex);
            swapValues(newIndex, parentIndex);
            newIndex = parentIndex;
        }

        return true;
    }
    // swaps two elements at the given indexes, and returns the second index.
    private int swapValues(int firstIndex, int secondIndex) {
        E temp = values[firstIndex];
        values[firstIndex] = values[secondIndex];
        values[secondIndex] = temp;

        return secondIndex;
    }

    // Test if the heap has reach capacity
    private boolean arrayIsFull() {
        return nextEmptyIndex == values.length;

    }

    // Doubles available space in the heap
    private void doubleArraySpace() {
        values = Arrays.copyOf(values, values.length * 2);
    }

    // Checks if priority of given index is higher than it's parent index.
    private boolean hasHigherPriorityThanParent(int newIndex) {
        return hasHigherPriority(newIndex, getParentIndex(newIndex));
    }

    // Tests if the priority at the first index is greater than the second
    private boolean hasHigherPriority(int first, int second) {
        if (second < 0)
            return false;

        E leftElement = values[first];
        E rightElement = values[second];

        return comparator.compare(leftElement,rightElement) < 0;
    }

    // Gets parent index given the child index
    private int getParentIndex(int childIndex) {
        int parentIndex = (childIndex + nAryMinusOne) / nAry - 1;
        return parentIndex;
    }

    /**
     * Retrieves the first item in the queue, but does not remove it
     *
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public E peek() {
        if (size() < 1)
            throw new IllegalStateException("Queue is empty. Cannot peek an empty queue.");
        return values[0];
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
    public E remove() {
        if (size() < 1)
            throw new IllegalStateException("Queue is empty. Cannot remove something from an empty queue.");

        int workingIndex = 0;
        E removedElement = values[workingIndex];
        moveLastValueToRoot();

        while (hasAChildWithHigherPriority(workingIndex)) {
            int childIndex = getIndexOfChildWithHighestPriority(workingIndex);
            swapValues(workingIndex, childIndex);
            workingIndex = childIndex;
        }

        return removedElement;
    }

    // Moves last item on the heap, to the root element.
    private void moveLastValueToRoot() {
        nextEmptyIndex--;
        values[0] = values[nextEmptyIndex];
    }


    // Tests if the given parent has a child of priority greater than itself.
    private boolean hasAChildWithHigherPriority(int parent) {
        E parentElement = values[parent];
        int[] children = getChildIndexes(parent);

        for (int child : children) {
            if (!indexExists(child))
                return false;

            E childElement = values[child];
            if (comparator.compare(childElement, parentElement) < 0)
                return true;
        }
        return false;
    }

    // Tests if the given index exists in the heap
    private boolean indexExists(int index) {
        return index < nextEmptyIndex;
    }

    // builds an array of children indexes, given the parent index.
    private int[] getChildIndexes(int parent) {
        int[] children = new int[nAry];

        for (int i = 0; i < nAry; i++)
            children[i] = (parent + 1) * nAry - nAryMinusOne + i;

        return children;
    }

    // Searches children of the given index and returns the child's index with the greatest priority
    private int getIndexOfChildWithHighestPriority(int index) {
        int[] children = getChildIndexes(index);

        int greatestChildIndex = children[0];
        E highestPriorityChild = values[children[0]];

        for (int childIndex : children) {
            if (!indexExists(childIndex))
                return greatestChildIndex;

            E child = values[childIndex];
            if (comparator.compare(child, highestPriorityChild) < 0) {
                greatestChildIndex = childIndex;
                highestPriorityChild = child;
            }

        }
        return greatestChildIndex;
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
     * queue, this method could be very helpful in testing the queue.
     * <p/>
     * The array object returned by this method is independent of the
     * queue. That is, the caller may reorder or replace values within
     * the returned array without any impact on the priority queue.
     *
     * @return An array containing the elements of this queue
     */
    @Override
    public Object[] toArray() {
        Object[] elements = new Object[nextEmptyIndex];
        for (int i = 0; i < nextEmptyIndex; i++) {
            elements[i] = values[i];
        }
        return elements;
    }
}
