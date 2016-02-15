package ad325.data_structures;

import java.util.Comparator;

/**
 * This is a priority queue, implemented using a heap.
 *
 * @Author Nathan Flint
 * Assignment: 4
 * Level: Extra Plus
 */
public class PrioritizedWords implements PriorityStringQueueInterface {
    private PriorityQueue<PriorityString> queue;

    /**
     * Constructor. Builds a new String priority queue.
     */
    public PrioritizedWords() {
        queue = new PriorityQueue<PriorityString>(new MinHeapComparator());
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
            throw new IllegalArgumentException("s string is null or empty. Null or empty strings not allowed");

        return queue.add(new PriorityString(s, p));
    }

    /**
     * Retrieves the first item in the queue, but does not remove it
     *
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public String peek() {
        return queue.peek().value;
    }

    /**
     * Empties the current priority queue.
     */
    @Override
    public void clear() {
        queue.clear();
    }

    /**
     * Retrieves the first item in the queue and removes it
     *
     * @return The value of the first item in the queue
     * @throws IllegalStateException if the queue is empty
     */
    @Override
    public String remove() {
        return queue.remove().value;
    }

    /**
     * Retrieves the number of elements in the queue.
     *
     * @return The number of elements in the queue
     */
    @Override
    public int size() {
        return queue.size();
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
        Object[] values = queue.toArray();
        for (int i = 0; i < queue.size(); i++) {
            values[i] = ((PriorityString)values[i]).value;
        }
        return values;
    }
}

/**
 * Composite class that holds a value and a priority.
 */
class PriorityString {
    // the heap priority of this element
    final int priority;
    // the string being stored in the queue.
    final String value;

    /**
     * Constructor.
     * @param value string value that will be stored
     * @param priority heap priority of the string
     */
    PriorityString(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }
}

/**
 * This comparator makes a Min Heap.
 */
class MinHeapComparator implements Comparator<PriorityString> {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p/>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.<p>
     * <p/>
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     * <p/>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p/>
     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p/>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(PriorityString o1, PriorityString o2) {
        return o1.priority - o2.priority;
    }
}
