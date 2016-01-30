/**
 * Created by nathanf on 1/21/2016.
 */
public class AVLSet implements StringSet_Improved, StringSet_Check {
    // The root node for the binary tree
    protected AVLNode root;
    // Tracks the number of things in the set
    private int nodeCount;
    // collection of rotation types
    private Rotation[] rotations;

    /**
     * Constructor initializes class
     */
    public AVLSet() {
        rotations = new Rotation[] {
                new LeftRotation(),
                new LeftRightRotation(),
                new RightRotation(),
                new RightLeftRotation()
        };
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
     * @throws NullPointerException if the specified
     *                              element is null
     */
    @Override
    public boolean add(String s) {
        if (s == null)
            throw new NullPointerException("Given string is null. Cannot add null string!");

        if (root == null) {
            root = new AVLNode(s);
            nodeCount++;
            return true;
        }

        if (recursiveAdd(null, root, s)) {
            nodeCount++;
            return true;
        }
        return false;
    }

    private boolean recursiveAdd(AVLNode parent, AVLNode node, String s) {
        if (node.value.equals(s))
            return false;

        AVLNode nextAVLNode = getNextAVLNode(node, s);

        if (nextAVLNode != null)
            if (recursiveAdd(node, nextAVLNode,s)) {
                updateHeight(node);
                rebalanceIfNeeded(parent, node);
                return true;
            } else {
                return false;
            }

        addStringToCorrectEdge(node, s);
        updateHeight(node);
        return true;

    }

    private void updateHeight(AVLNode node) {
        if (node == null)
            return;
        node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
    }

    private void rebalanceIfNeeded(AVLNode parent, AVLNode node) {
        for (Rotation rotation : rotations)
            if (rotation.isNeeded(node)) {
                rotation.execute(parent, node);
                return;
            }
    }

    private int getNodeHeight(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    // Used for adding a new leaf. Adds node to correct edge.
    private void addStringToCorrectEdge(AVLNode workingAVLNode, String s) {
        if (stringIsLessThanAVLNode(workingAVLNode, s))
            workingAVLNode.left = new AVLNode(s);
        else
            workingAVLNode.right = new AVLNode(s);
    }

    // returns left or right node base of value of given string
    private AVLNode getNextAVLNode(AVLNode workingAVLNode, String s) {
        return stringIsLessThanAVLNode(workingAVLNode, s) ? workingAVLNode.left : workingAVLNode.right;
    }

    // identifies if given string is less than given node
    private boolean stringIsLessThanAVLNode(AVLNode workingAVLNode, String stringToRemove) {
        return workingAVLNode.value.compareTo(stringToRemove) > 0;
    }

    // identifies if given string is greater than given node
    private boolean stringIsGreaterThanAVLNode(AVLNode workingAVLNode, String stringToRemove) {
        return workingAVLNode.value.compareTo(stringToRemove) < 0;
    }

    /**
     * Removes all of the elements from this set. The set
     * will be empty after this call returns.
     */
    @Override
    public void clear() {
        root = null;
        nodeCount = 0;
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
        if (s == null)
            throw new NullPointerException("Given string is null. Cannot identify if set contains a null string!");

        if (root == null)
            return false;

        return recursiveContains(root, s);
    }

    // Traverses the given node looking for the given string value.
    // Returns true if found, false if not found
    private boolean recursiveContains(AVLNode startingAVLNode, String s) {
        if (startingAVLNode == null)
            return false;

        if (startingAVLNode.value.equals(s))
            return true;

        if (stringIsLessThanAVLNode(startingAVLNode, s))
            return recursiveContains(startingAVLNode.left, s);

        return recursiveContains(startingAVLNode.right, s);
    }

    /**
     * Returns true if this set contains no elements.
     *
     * @return true if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return root == null;
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
        return "[" + toStringInOrderRecursive(root) + "]";
    }

    // Builds a string of tree elements recursively from the given
    // node using In-Order sequence
    private String toStringInOrderRecursive(AVLNode currentNode) {
        if (currentNode == null)
            return "";

        String optionalPreSpace = currentNode.left == null ? "" : ", ";
        String optionalPostSpace = currentNode.right == null ? "" : ", ";

        return toStringInOrderRecursive(currentNode.left)
                + optionalPreSpace
                + currentNode.value
                + optionalPostSpace
                + toStringInOrderRecursive(currentNode.right);
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
        return "[" + toStringPreOrderRecursivce(root) + "]";
    }

    // Builds a string of tree elements recursively from the given
    // node using Pre-Order sequence
    private String toStringPreOrderRecursivce(AVLNode currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = nodeIsRoot(currentNode) ? "" : ", ";

        return optionalSpace
                + currentNode.value
                + toStringPreOrderRecursivce(currentNode.left)
                + toStringPreOrderRecursivce(currentNode.right);
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
        return "[" + toStringPostOrderRecursive(root) + "]";
    }

    // Builds a string of tree elements recursively from the given
    // node using Post-Order sequence
    private String toStringPostOrderRecursive(AVLNode currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = nodeIsRoot(currentNode) ? "" : ", ";

        return toStringPostOrderRecursive(currentNode.left)
                + toStringPostOrderRecursive(currentNode.right)
                + currentNode.value
                + optionalSpace;
    }

    /**
     * Prints an unambiguous string representation of the tree's structure.
     * @return string representing where all values lie in the tree
     */
    @Override
    public String toString() {
        return getStringRepresentationRecursive(root);
    }

    // recursively builds a string that represents the tree's structure
    private String getStringRepresentationRecursive(AVLNode node) {
        if (node == null)
            return "_";

        String left = getStringRepresentationRecursive(node.left);
        String right = getStringRepresentationRecursive(node.right);
        String optionalSpace = " ";

        if ((left == "_") && (right == "_")) {
            return node.value;
        }

        return "(" + node.value + optionalSpace + left + optionalSpace + right + ")";
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
     *          if present
     * @return true if this set contained the specified
     * element
     * @throws NullPointerException if the specified
     *                              element is null
     */
    @Override
    public boolean remove(String s) {
        if (recursiveRemove(null ,root, s)) {
            nodeCount--;
            return true;
        }

        return false;
    }

    // Recursively finds and then removes the given string from the given root node.
    private boolean recursiveRemove(AVLNode lastAVLNode, AVLNode workingAVLNode, String stringToRemove) {
        if (workingAVLNode == null)
            return false;

        if (stringIsLessThanAVLNode(workingAVLNode, stringToRemove))
            return recursiveRemove(workingAVLNode, workingAVLNode.left, stringToRemove);

        if (stringIsGreaterThanAVLNode(workingAVLNode, stringToRemove))
            return recursiveRemove(workingAVLNode, workingAVLNode.right, stringToRemove);

        doRemove(lastAVLNode, workingAVLNode);
        return true;
    }

    // Removes the given node from the given parent.
    private void doRemove(AVLNode parentAVLNode, AVLNode nodeToRemove) {
        if (nodeToRemove.left == null) {
            updateParentReference(parentAVLNode, nodeToRemove, nodeToRemove.right);
            return;
        }

        if (nodeToRemove.right == null) {
            updateParentReference(parentAVLNode, nodeToRemove, nodeToRemove.left);
            return;
        }

        String maxLeftValue = findMaxValue(nodeToRemove.left);
        nodeToRemove.value = maxLeftValue;
        recursiveRemove(nodeToRemove, nodeToRemove.left, maxLeftValue);
    }

    // recursively searches the given tree looking for the maximum value
    private String findMaxValue(AVLNode currentAVLNode) {
        if (currentAVLNode.right == null)
            return currentAVLNode.value;
        return findMaxValue(currentAVLNode.right);
    }

    // Updates the parent's reference to a new child.
    private void updateParentReference(AVLNode parent, AVLNode childToRemove, AVLNode newChild) {
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

    // Identifies if the given node is the root node
    private boolean nodeIsRoot(AVLNode currentNode) {
        return currentNode == root;
    }

    // Identifies if the given child is the right node of the given parent
    private boolean isRightChildOfParent(AVLNode parentAVLNode, AVLNode childAVLNode) {
        return parentAVLNode.right == childAVLNode;
    }

    /**
     * Returns the number of elements in this set (its
     * cardinality).
     *
     * @return the number of elements in this set (its
     * cardinality)
     */
    @Override
    public int size() {
        return nodeCount;
    }

    /**
     * AVLNode for AVLSet
     */
    class AVLNode {
        String value;
        AVLNode left, right;//, parent;
        int height;

        public AVLNode(String s) {
            value = s;
            left = right = null;
            height = 1;
        }

    }

    private class LeftRightRotation extends Rotation {

        @Override
        boolean isNeeded(AVLNode node) {
            return leftChildIsTooLong(node) && isRightChildLongest(node.left);
        }

        @Override
        void execute(AVLNode parent, AVLNode rotatingNode) {
            rotateLeft(rotatingNode, rotatingNode.left);
            rotateRight(parent, rotatingNode);
        }
    }

    private class RightRotation extends Rotation {

        @Override
        boolean isNeeded(AVLNode node) {
            return leftChildIsTooLong(node) && !isRightChildLongest(node.left);
        }

        @Override
        void execute(AVLNode parent, AVLNode rotatingNode) {
            rotateRight(parent, rotatingNode);
        }
    }

    private class LeftRotation extends Rotation {

        @Override
        boolean isNeeded(AVLNode node) {
            return rightChildIsTooLong(node) && isRightChildLongest(node.right);
        }

        @Override
        void execute(AVLNode parent, AVLNode rotatingNode) {
            rotateLeft(parent, rotatingNode);
        }
    }
    private class RightLeftRotation extends Rotation {

        @Override
        boolean isNeeded(AVLNode node) {
            return rightChildIsTooLong(node) && !isRightChildLongest(node.right);
        }

        @Override
        void execute(AVLNode parent, AVLNode rotatingNode) {
            rotateRight(rotatingNode, rotatingNode.right);
            rotateLeft(parent, rotatingNode);
        }
    }

    private abstract  class Rotation {
        abstract boolean isNeeded(AVLNode node);
        abstract void execute(AVLNode parent, AVLNode rotatingNode);

        protected int getNodeHeight(AVLNode node) {
            return node == null ? 0 : node.height;
        }

        protected boolean leftChildIsTooLong(AVLNode node) {
            return (getNodeHeight(node.left) - getNodeHeight(node.right)) > 1;
        }

        protected boolean rightChildIsTooLong(AVLNode node) {
            return (getNodeHeight(node.right) - getNodeHeight(node.left)) > 1;
        }

        protected boolean isRightChildLongest(AVLNode node) {
            if (node == null) return false;

            int rightHeight = node.right == null ? 0 : node.right.height;
            int leftHeight = node.left == null ? 0 : node.left.height;
            return rightHeight > leftHeight;
        }

        protected void rotateLeft(AVLNode parent, AVLNode rotatingNode) {
            AVLNode temp = rotatingNode.right;
            rotatingNode.right = temp.left;
            temp.left = rotatingNode;

            updateParentReference(parent, rotatingNode, temp);
            updateHeight(rotatingNode);
            updateHeight(temp);
            updateHeight(parent);
        }

        protected void rotateRight(AVLNode parent, AVLNode rotatingNode) {
            AVLNode temp = rotatingNode.left;
            rotatingNode.left = temp.right;
            temp.right = rotatingNode;

            updateParentReference(parent, rotatingNode, temp);
            updateHeight(rotatingNode);
            updateHeight(temp);
            updateHeight(parent);
        }
    }
}
