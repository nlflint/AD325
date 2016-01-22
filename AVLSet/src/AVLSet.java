/**
 * Created by nathanf on 1/21/2016.
 */
public class AVLSet implements StringSet_Improved {
    protected AVLNode root;

    /**
     * Adds the specified element to this set if it is
     * not already present. More formally, adds the
     * specified element s to this set if the set contains
     * no element s2 such that s.equals(s2). If this
     * set already contains the element, the call leaves
     * the set unchanged and returns false. This ensures
     * that the set never contains duplicate elements.
     *
     * @param s element to be added to this set
     * @return true if this set did not already contain
     * the specified element
     * @throws NullPointerException if the specified
     *                              element is null
     */
    @Override
    public boolean add(String s) {
        if (root == null) {
            root = new AVLNode(s);
            return true;
        }

        return recursiveAdd(root, s);
    }

    private boolean recursiveAdd(AVLNode workingNode, String s) {
        if (workingNode.value.equals(s))
            return false;

        AVLNode nextNode = getNextNode(workingNode, s);

        if (nextNode != null)
            return recursiveAdd(nextNode,s);

        addStringToCorrectEdge(workingNode, s);

        return true;

    }

    // Used for adding a new leaf. Adds node to correct edge.
    private void addStringToCorrectEdge(AVLNode workingNode, String s) {
        if (stringIsLessThanNode(workingNode, s))
            workingNode.left = new AVLNode(s);
        else
            workingNode.right = new AVLNode(s);
    }

    // returns left or right node base of value of given string
    private AVLNode getNextNode(AVLNode workingNode, String s) {
        return stringIsLessThanNode(workingNode, s) ? workingNode.left : workingNode.right;
    }

    // identifies if given string is less than given node
    private boolean stringIsLessThanNode(AVLNode workingNode, String stringToRemove) {
        return workingNode.value.compareTo(stringToRemove) > 0;
    }

    // identifies if given string is greater than given node
    private boolean stringIsGreaterThanNode(AVLNode workingNode, String stringToRemove) {
        return workingNode.value.compareTo(stringToRemove) < 0;
    }

    /**
     * Removes all of the elements from this set. The set
     * will be empty after this call returns.
     */
    @Override
    public void clear() {

    }

    /**
     * Returns true if this set contains the
     * specified element. More formally, returns true
     * if and only if this set contains an element e
     * such that s.equals(e).
     *
     * @param s element whose presence in this set is
     *          to be tested
     * @return true if this set contains the specified
     * element
     * @throws NullPointerException if the specified
     *                              element is null
     */
    @Override
    public boolean contains(String s) {
        return false;
    }

    /**
     * Returns true if this set contains no elements.
     *
     * @return true if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns a String containing all of the elements in this set ordered
     * using an in-order traversal of the underlying tree, with punctuation.
     * The return value is enclosed in square brackets with values separated
     * by the sequence comma-space.
     * <p>Here are some example: <ul>
     * <li>The set containing "A" "B" "C" would return: <br>
     * <tt>[A, B, C]</tt></li>
     * <li>The set containing "Hi" would return: <br>
     * <tt>[Hi]</tt></li>
     * <li>The empty set would return: <br>
     * <tt>[]</tt></li></ul>
     *
     * @return A string representation of the elements of the set ordered
     * by an in-order traversal of the underlying tree, with punctuation.
     */
    @Override
    public String toStringInOrder() {
        return null;
    }

    /**
     * Returns a String containing all of the elements in this set ordered
     * using a pre-order traversal of the underlying tree, with punctuation.
     * The return value is enclosed in square brackets with values separated
     * by the sequence comma-space.
     * <p>Here are some example: <ul>
     * <li>The set containing "A" "B" "C" would return: <br>
     * <tt>[B, A, C]</tt></li>
     * <li>The set containing "Hi" would return: <br>
     * <tt>[Hi]</tt></li>
     * <li>The empty set would return: <br>
     * <tt>[]</tt></li></ul>
     *
     * @return A string representation of the elements of the set ordered
     * by a pre-order traversal of the underlying tree, with punctuation.
     */
    @Override
    public String toStringPreOrder() {
        return null;
    }

    /**
     * Returns a String containing all of the elements in this set ordered
     * using a post-order traversal of the underlying tree, with punctuation.
     * The return value is enclosed in square brackets with values separated
     * by the sequence comma-space.
     * <p>Here are some example: <ul>
     * <li>The set containing "A" "B" "C" would return: <br>
     * <tt>[A, C, B]</tt></li>
     * <li>The set containing "Hi" would return: <br>
     * <tt>[Hi]</tt></li>
     * <li>The empty set would return: <br>
     * <tt>[]</tt></li></ul>
     *
     * @return A string representation of the elements of the set ordered
     * by a post-order traversal of the underlying tree, with punctuation.
     */
    @Override
    public String toStringPostOrder() {
        return null;
    }

    /**
     * Node for AVLSet
     */
    class AVLNode {
        String value;
        AVLNode left, right, parent;

        public AVLNode(String s) {
            value = s;
            left = right = parent = null;
        }

    }
}
