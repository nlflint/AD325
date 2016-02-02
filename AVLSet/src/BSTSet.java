/**
 * This class implements a Set. A binary search tree is used for the implementation.
 *
 * @Author Nathan Flint
 */
public class BSTSet {
    // a string that will delineate the items returned by the three toString methods.
    protected String DELIMITER;

    // The root node of the binary tree
    protected Node root;

    // The number of nodes in the tree
    protected int numberOfNodes;

    /**
     * Constructor initializes an empty set
     */
    public BSTSet() {
        numberOfNodes = 0;
        root = null;
        DELIMITER = " ";
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
        }
        else if (!recursiveAdd(null, root, s)) {
            return false;
        }

        numberOfNodes++;
        return true;
    }

    // Recursively traverses the tree looking for the right
    // place to put the given string.
    private boolean recursiveAdd(Node parent, Node node, String s) {
        if (node.value.equals(s))
            return false;

        Node nextNode = getNextNode(node, s);

        if (nextNode != null)
            if (recursiveAdd(node, nextNode,s)) {
                Node.updateHeight(node);
                rebalanceIfNeeded(parent, node);
                return true;
            } else {
                return false;
            }

        addStringToCorrectEdge(node, s);
        Node.updateHeight(node);
        return true;
    }

    /**
     * Rebalances the given node if it is unbalanced.
     * @param parent The parent node of the node to be rebalanced
     * @param node The node that will be checked for balance and then rebalanced if needed.
     */
    protected void rebalanceIfNeeded(Node parent, Node node) {
        // Do nothing. BSTTree is not rebalanced.
    }

    // Used for adding a new leaf. Adds node to correct edge.
    private void addStringToCorrectEdge(Node workingNode, String s) {
        if (stringIsLessThanNode(workingNode, s))
            workingNode.left = new Node(s);
        else
            workingNode.right = new Node(s);
    }

    // returns left or right node base of value of given string
    private Node getNextNode(Node workingNode, String s) {
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
    private boolean recursiveRemove(Node parent, Node workingNode, String stringToRemove) {
        if (workingNode == null)
            return false;

        boolean result;
        if (stringIsLessThanNode(workingNode, stringToRemove))  {
            result = recursiveRemove(workingNode, workingNode.left, stringToRemove);
        }
        else if (stringIsGreaterThanNode(workingNode, stringToRemove)) {
            result = recursiveRemove(workingNode, workingNode.right, stringToRemove);
        }
        else {
            doRemove(parent, workingNode);
            result = true;
        }

        Node.updateHeight(workingNode);
        rebalanceIfNeeded(parent, workingNode);
        return result;
    }

    // identifies if given string is less than given node
    private boolean stringIsLessThanNode(Node workingNode, String stringToRemove) {
        return workingNode.value.compareTo(stringToRemove) > 0;
    }

    // identifies if given string is greater than given node
    private boolean stringIsGreaterThanNode(Node workingNode, String stringToRemove) {
        return workingNode.value.compareTo(stringToRemove) < 0;
    }

    // Removes the given node from the given parent.
    private void doRemove(Node parentNode, Node nodeToRemove) {
        if (nodeToRemove.left == null) {
            replaceChildOfParent(parentNode, nodeToRemove, nodeToRemove.right);
            return;
        }

        if (nodeToRemove.right == null) {
            replaceChildOfParent(parentNode, nodeToRemove, nodeToRemove.left);
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

    /**
     * Updates the parent's reference to its right or left child. Uses old child reference to determine
     * which child to replace (left or right).
     * @param parent The parent reference that will be updated
     * @param oldChild The current child reference that will be removed from the parent
     * @param newChild The new child that will replace the old child
     */
    protected void replaceChildOfParent(Node parent, Node oldChild, Node newChild) {
        if (isRootNode(oldChild))
            root = newChild;
        else if(isRightChildOfParent(parent, oldChild))
            parent.right = newChild;
        else
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

        String optionalPreSpace = currentNode.left == null ? "" : DELIMITER;
        String optionalPostSpace = currentNode.right == null ? "" : DELIMITER;

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

        String optionalSpace = isRootNode(currentNode) ? "" : DELIMITER;

        return optionalSpace
                + currentNode.value
                + toStringPreOrderRecursivce(currentNode.left)
                + toStringPreOrderRecursivce(currentNode.right);
    }

    // Identifies if the given node is the root node
    boolean isRootNode(Node currentNode) {
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

        String optionalSpace = isRootNode(currentNode) ? "" : DELIMITER;

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
    // The left and right children of this node
    Node left, right;
    // height of the node
    private int height;

    /**
     * Constructor. Makes a new node with the given value and null child references.
     * @param s value that this node will hold
     */
    public Node(String s) {
        value = s;
        left = right = null;
        height = 1;
    }

    /**
     * Updates the height of the given node using the pre-calculated height of it's children.
     // Does not traverse tree to figure out height.
     * @param node The Node that will have its height updated
     */
    public static void updateHeight(Node node) {
        if (node == null)
            return;
        node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
    }

    /**
     * Gets height of a node safely. e.g. Returns 0 if node is null.
     * @param node Node from which to get height
     * @return height of the given node in the tree
     */
    public static int getNodeHeight(Node node) {
        return node == null ? 0 : node.height;
    }
}

