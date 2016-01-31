/**
 * Created by nathanf on 1/21/2016.
 */
public class AVLSet extends BSTSet implements StringSet_Improved, StringSet_Check  {
    // collection of rotation types
    private Rotation[] rotations;

    /**
     * Constructor initializes class
     */
    public AVLSet() {
        rotations = new Rotation[] {
                new LeftRotation(),
                new RightRotation(),
                new LeftRightRotation(),
                new RightLeftRotation()
        };
    }

    @Override
    protected boolean recursiveAdd(Node parent, Node node, String s) {
        if (node.value.equals(s))
            return false;

        Node nextNode = getNextNode(node, s);

        if (nextNode != null)
            if (recursiveAdd(node, nextNode,s)) {
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

    private void updateHeight(Node node) {
        if (node == null)
            return;
        node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
    }

    private void rebalanceIfNeeded(Node parent, Node node) {
        for (Rotation rotation : rotations)
            if (rotation.isNeeded(node)) {
                rotation.execute(parent, node);
                return;
            }
    }

    private int getNodeHeight(Node node) {
        return node == null ? 0 : node.height;
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
    private String toStringInOrderRecursive(Node currentNode) {
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
    private String toStringPreOrderRecursivce(Node currentNode) {
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
    private String toStringPostOrderRecursive(Node currentNode) {
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
    private String getStringRepresentationRecursive(Node node) {
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

        updateHeight(workingNode);
        rebalanceIfNeeded(parent, workingNode);
        return result;
    }

    // Removes the given node from the given parent.
    private void doRemove(Node parentAVLNode, Node nodeToRemove) {
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
    private String findMaxValue(Node currentAVLNode) {
        if (currentAVLNode.right == null)
            return currentAVLNode.value;
        return findMaxValue(currentAVLNode.right);
    }

    private class LeftRightRotation extends Rotation {

        @Override
        boolean isNeeded(Node node) {
            return leftChildIsTooLong(node) && isRightChildLongest(node.left);
        }

        @Override
        void execute(Node parent, Node rotatingNode) {
            rotateLeft(rotatingNode, rotatingNode.left);
            rotateRight(parent, rotatingNode);
        }
    }

    private class RightRotation extends Rotation {

        @Override
        boolean isNeeded(Node node) {
            return leftChildIsTooLong(node) && isLeftChildLongest(node.left);
        }

        @Override
        void execute(Node parent, Node rotatingNode) {
            rotateRight(parent, rotatingNode);
        }
    }

    private class LeftRotation extends Rotation {

        @Override
        boolean isNeeded(Node node) {
            return rightChildIsTooLong(node) && isRightChildLongest(node.right);
        }

        @Override
        void execute(Node parent, Node rotatingNode) {
            rotateLeft(parent, rotatingNode);
        }
    }
    private class RightLeftRotation extends Rotation {

        @Override
        boolean isNeeded(Node node) {
            return rightChildIsTooLong(node) && isLeftChildLongest(node.right);
        }

        @Override
        void execute(Node parent, Node rotatingNode) {
            rotateRight(rotatingNode, rotatingNode.right);
            rotateLeft(parent, rotatingNode);
        }
    }

    private abstract  class Rotation {
        abstract boolean isNeeded(Node node);
        abstract void execute(Node parent, Node rotatingNode);

        protected int getNodeHeight(Node node) {
            return node == null ? 0 : node.height;
        }

        protected boolean leftChildIsTooLong(Node node) {
            return (getNodeHeight(node.left) - getNodeHeight(node.right)) > 1;
        }

        protected boolean rightChildIsTooLong(Node node) {
            return (getNodeHeight(node.right) - getNodeHeight(node.left)) > 1;
        }

        protected boolean isRightChildLongest(Node node) {
            if (node == null) return false;

            int rightHeight = node.right == null ? 0 : node.right.height;
            int leftHeight = node.left == null ? 0 : node.left.height;
            return rightHeight >= leftHeight;
        }

        protected boolean isLeftChildLongest(Node node) {
            if (node == null) return false;

            int rightHeight = node.right == null ? 0 : node.right.height;
            int leftHeight = node.left == null ? 0 : node.left.height;
            return leftHeight >= rightHeight;
        }

        protected void rotateLeft(Node parent, Node rotatingNode) {
            Node temp = rotatingNode.right;
            rotatingNode.right = temp.left;
            temp.left = rotatingNode;

            updateParentReference(parent, rotatingNode, temp);
            updateHeight(rotatingNode);
            updateHeight(temp);
            updateHeight(parent);
        }

        protected void rotateRight(Node parent, Node rotatingNode) {
            Node temp = rotatingNode.left;
            rotatingNode.left = temp.right;
            temp.right = rotatingNode;

            updateParentReference(parent, rotatingNode, temp);
            updateHeight(rotatingNode);
            updateHeight(temp);
            updateHeight(parent);
        }
    }
}
