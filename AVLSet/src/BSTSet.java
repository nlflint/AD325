/**
 * Nathan Flint
 * Assignment 2: BSTSet
 * Level: Plus
 * Features:
 *   -toString enhancement
 *   -remove
 *   -simple iterators
 *   -complicated iterator
 *
 * This class implements a Set. A binary search tree is used for the implementation.
 *
 * @author Nathan Flint
 * 28 January 2016
 */
public class BSTSet {
    // a string that will delineate the items returned by the three toString methods.
    protected String DELIMINATOR;

    // The root node of the binary tree
    Node root;

    // The number of nodes in the tree
    int numberOfNodes;

    /**
     * Constructor initializes an empty set
     */
    public BSTSet() {
        numberOfNodes = 0;
        root = null;
        DELIMINATOR = " ";
    }

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
     * @throws IllegalArgumentException if the specified
     * element is null
     */
    public boolean add(String s) {
        if (s == null)
            throw new IllegalArgumentException("Given string is null. Cannot add null string!");

        if (root == null) {
            root = new Node(s);
            numberOfNodes++;
            return true;
        }

        if (recursiveAdd(null, root, s)) {
            numberOfNodes++;
            return true;
        }
        return false;
    }

    // Recursively traverses the tree looking for the right
    // place to put the given string.
    protected boolean recursiveAdd(Node parent, Node workingNode, String s) {
        if (workingNode.value.equals(s))
            return false;

        Node nextNode = getNextNode(workingNode, s);

        if (nextNode != null)
            return recursiveAdd(parent, nextNode,s);

        addStringToCorrectEdge(workingNode, s);

        return true;
    }

    // Used for adding a new leaf. Adds node to correct edge.
    protected void addStringToCorrectEdge(Node workingNode, String s) {
        if (stringIsLessThanNode(workingNode, s))
            workingNode.left = new Node(s);
        else
            workingNode.right = new Node(s);
    }

    // returns left or right node base of value of given string
    protected Node getNextNode(Node workingNode, String s) {
        return stringIsLessThanNode(workingNode, s) ? workingNode.left : workingNode.right;
    }

    /**
     * Returns true if this set contains the
     * specified element. More formally, returns true
     * if and only if this set contains an element e
     * such that s.equals(e).
     *
     * @param s element whose presence in this set is
     * to be tested
     * @return true if this set contains the specified
     * element
     * @throws IllegalArgumentException if the specified
     * element is null
     */
    public boolean contains(String s) {
        if (s == null)
            throw new IllegalArgumentException("Given string is null. Cannot identify if set contains a null string!");

        if (root == null)
            return false;

        return recursiveContains(root, s);
    }

    // Traverses the given node looking for the given string value.
    // Returns true if found, false if not found
    private boolean recursiveContains(Node startingNode, String s) {
        if (startingNode == null)
            return false;

        if (startingNode.value.equals(s))
            return true;

        if (stringIsLessThanNode(startingNode, s))
            return recursiveContains(startingNode.left, s);

        return recursiveContains(startingNode.right, s);
    }

    /**
     * Removes the specified element from this set if
     * it is present. More formally, this removes an
     * element e such that s.equals(e), if this set
     * contains such an element. Returns true if this
     * set contained the element (or equivalently, if
     * this set changed as a result of the call).
     * (This set will not contain the element once the
     * call returns.)
     *
     * @param s the String to be removed from this set,
     * if present
     * @return true if this set contained the specified
     * element
     * @throws IllegalArgumentException if the specified
     * element is null
     */
    public boolean remove(String s) {
        if (s == null)
            throw new IllegalArgumentException("Given string is null. Cannot remove a null string!");

        if (recursiveRemove(null ,root, s)) {
            numberOfNodes--;
            return true;
        }

        return false;
    }

    // Recursively finds and then removes the given string from the given root node.
    private boolean recursiveRemove(Node lastNode, Node workingNode, String stringToRemove) {
        if (workingNode == null)
            return false;

        if (stringIsLessThanNode(workingNode, stringToRemove))
            return recursiveRemove(workingNode, workingNode.left, stringToRemove);

        if (stringIsGreaterThanNode(workingNode, stringToRemove))
            return recursiveRemove(workingNode, workingNode.right, stringToRemove);

        doRemove(lastNode, workingNode);
        return true;
    }

    // identifies if given string is less than given node
    protected boolean stringIsLessThanNode(Node workingNode, String stringToRemove) {
        return workingNode.value.compareTo(stringToRemove) > 0;
    }

    // identifies if given string is greater than given node
    protected boolean stringIsGreaterThanNode(Node workingNode, String stringToRemove) {
        return workingNode.value.compareTo(stringToRemove) < 0;
    }

    // Removes the given node from the given parent.
    private void doRemove(Node parentNode, Node nodeToRemove) {
        if (nodeToRemove.left == null) {
            updateParentReference(parentNode, nodeToRemove, nodeToRemove.right);
            return;
        }

        if (nodeToRemove.right == null) {
            updateParentReference(parentNode, nodeToRemove, nodeToRemove.left);
            return;
        }

        String maxLeftValue = findMaxValue(nodeToRemove.left);
        nodeToRemove.value = maxLeftValue;
        recursiveRemove(nodeToRemove, nodeToRemove.left, maxLeftValue);
    }

    // recursively searches the given tree looking for the maximum value
    private String findMaxValue(Node currentNode) {
        if (currentNode.right == null)
            return currentNode.value;
        return findMaxValue(currentNode.right);
    }

    // Updates the parent's reference to a new child.
    protected void updateParentReference(Node parent, Node childToRemove, Node newChild) {
        // Special case if removing root node
        if (nodeIsRoot(childToRemove)) {
            root = newChild;
            return;
        }

        if(isRightChildOfParent(parent, childToRemove)) {
            parent.right = newChild;
            return;
        }

        parent.left = newChild;
        return;
    }

    // Identifies if the given child is the right node of the given parent
    private boolean isRightChildOfParent(Node parentNode, Node childNode) {
        return parentNode.right == childNode;
    }

    /**
     * Returns the number of elements in this set (its
     * cardinality).
     *
     * @return the number of elements in this set (its
     * cardinality)
     */
    public int size() {
        return numberOfNodes;
    }

    // Recursively counts the total number of nodes from the given node.
    private int recursiveSize(Node workingNode) {
        if (workingNode == null)
            return 0;
        return 1 + recursiveSize(workingNode.left) + recursiveSize(workingNode.right);
    }

    /**
     * Removes all of the elements from this set. The set
     * will be empty after this call returns.
     */
    public void clear() {
        root = null;
        numberOfNodes = 0;
    }

    /**
     * Returns true if this set contains no elements.
     *
     * @return true if this set contains no elements
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns a String containing all of the elements
     * in this set ordered using an in-order traversal
     * of the underlying tree. The values are separated
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     *
     * @return A string representation of the elements of
     * the set ordered by an in-order traversal of the
     * underlying tree.
     */
    public String toStringInOrder() {
        return "[" + toStringInOrderRecursive(root) + "]";
    }

    // Builds a string of tree elements recursively from the given
    // node using In-Order sequence
    private String toStringInOrderRecursive(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalPreSpace = currentNode.left == null ? "" : DELIMINATOR;
        String optionalPostSpace = currentNode.right == null ? "" : DELIMINATOR;

        return toStringInOrderRecursive(currentNode.left)
                + optionalPreSpace
                + currentNode.value
                + optionalPostSpace
                + toStringInOrderRecursive(currentNode.right);
    }

    /**
     * Returns a String containing all of the elements
     * in this set ordered using a pre-order traversal
     * of the underlying tree. The values are separated
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     *
     * @return A string representation of the elements of
     * the set ordered by a pre-order traversal of the
     * underlying tree.
     */
    public String toStringPreOrder() {
        return "[" + toStringPreOrderRecursivce(root) + "]";
    }

    // Builds a string of tree elements recursively from the given
    // node using Pre-Order sequence
    private String toStringPreOrderRecursivce(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = nodeIsRoot(currentNode) ? "" : DELIMINATOR;

        return optionalSpace
                + currentNode.value
                + toStringPreOrderRecursivce(currentNode.left)
                + toStringPreOrderRecursivce(currentNode.right);
    }

    // Identifies if the given node is the root node
    protected boolean nodeIsRoot(Node currentNode) {
        return currentNode == root;
    }

    /**
     * Returns a String containing all of the elements
     * in this set ordered using a post-order traversal
     * of the underlying tree. The values are separated
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     *
     * @return A string representation of the elements of
     * the set ordered by a post-order traversal of the
     * underlying tree.
     *
     */
    public String toStringPostOrder() {
        return "[" + toStringPostOrderRecursive(root) + "]";
    }

    // Builds a string of tree elements recursively from the given
    // node using Post-Order sequence
    private String toStringPostOrderRecursive(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = nodeIsRoot(currentNode) ? "" : DELIMINATOR;

        return toStringPostOrderRecursive(currentNode.left)
                + toStringPostOrderRecursive(currentNode.right)
                + currentNode.value
                + optionalSpace;
    }
}

/**
 * This is a node used in the tree
 */
class Node {
    // Value of the node
    String value;
    // The left and right childred of this node
    Node left, right;
    // height of the node
    int height;

    /**
     * Constructor. Makes a new node with the given value and null child references.
     * @param s value that this node will hold
     */
    public Node(String s) {
        value = s;
        left = right = null;
        height = 1;
    }
}

/**
 * This class implements the Stack ADT using a linked list.
 */
class NodeStack<T> {
    private StackNode<T> head;

    /**
     * Puts the given value on the top of the stack
     * @param number value to put on the stack
     */
    public void push(T number) {
        head = new StackNode<T>(number, head);
    }

    /**
     * Removes and returns the last item put on the stack.
     * @return value of the item removed from the stack.
     */
    public T pop() {
        VerifyStackIsNotEmpty();

        StackNode<T> poppedValue = head;
        head = head.next;
        return poppedValue.value;
    }

    private void VerifyStackIsNotEmpty() {
        if (isEmpty())
            throw new IllegalStateException();
    }

    /**
     * Identifies if the stack contains any items.
     * @return true if the stack is empty
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Gets the value from the top of the stack without removing it.
     * @return the value at the top of the stack
     */
    public T peek() {
        VerifyStackIsNotEmpty();
        return head.value;
    }
}

class StackNode<T> {
    public T value;
    public StackNode<T> next;
    public StackNode(T v) {
        value = v;
        next = null;
    }

    public StackNode(T v, StackNode<T> n) {
        value = v;
        next = n;
    }
}

