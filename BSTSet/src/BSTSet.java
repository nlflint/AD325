/**
 * Created by nate on 1/16/16.
 */
public class BSTSet implements StringSet_Plus {
    private Node root;

    @Override
    public StringIterator iteratorInOrder() {
        return null;
    }

    @Override
    public StringIterator iteratorPreOrder() {
        return null;
    }

    @Override
    public StringIterator iteratorPostOrder() {
        return null;
    }

    @Override
    public boolean remove(String s) {
        return recursiveRemove(null ,root, s);
    }

    private boolean recursiveRemove(Node lastNode, Node workingNode, String s) {
        if (workingNode == null)
            return false;

        if (workingNode.value.compareTo(s) > 0)
            return recursiveRemove(workingNode, workingNode.left, s);

        if (workingNode.value.compareTo(s) < 0)
            return recursiveRemove(workingNode, workingNode.right, s);

        doRemove(lastNode, workingNode);
        return true;
    }

    private void doRemove(Node parentNode, Node removingNode) {
        if (removingNode.left == null) {
            updateParentReference(parentNode, removingNode, removingNode.right);
            return;
        }

        if (removingNode.right == null) {
            updateParentReference(parentNode, removingNode, removingNode.left);
            return;
        }

        String maxLeftValue = findMaxValue(removingNode.left);
        removingNode.value = maxLeftValue;
        recursiveRemove(removingNode, removingNode.left, maxLeftValue);
    }

    private String findMaxValue(Node currentNode) {
        if (currentNode.right == null)
            return currentNode.value;
        return findMaxValue(currentNode.right);
    }

    private void updateParentReference(Node parentNode, Node removingNode, Node newChildNode) {
        if (parentNode == null) {
            root = newChildNode;
            return;
        }

        if(isRightChildOfParent(parentNode, removingNode)) {
            parentNode.right = newChildNode;
        } else {
            parentNode.left = newChildNode;
        }
        return;
    }

    private boolean isRightChildOfParent(Node parentNode, Node childNode) {
        return parentNode.right == childNode;
    }

    @Override
    public int size() {
        return recursiveSize(root);
    }

    private int recursiveSize(Node workingNode) {
        if (workingNode == null)
            return 0;
        return 1 + recursiveSize(workingNode.left) + recursiveSize(workingNode.right);
    }

    @Override
    public boolean add(String s) {
        if (root == null) {
            root = new Node(s);
            return true;
        }

        return recursiveAdd(root, s);
    }

    private boolean recursiveAdd(Node workingNode, String s) {
        if (workingNode.value.equals(s))
            return false;

        Node nextNode = getNextNode(workingNode, s);

        if (nextNode != null)
            return recursiveAdd(nextNode,s);

        addStringToCorrectEdge(workingNode, s);

        return true;
    }

    private void addStringToCorrectEdge(Node workingNode, String s) {
        if (workingNode.value.compareTo(s) > 0)
            workingNode.left = new Node(s);
        else
            workingNode.right = new Node(s);
    }

    private Node getNextNode(Node workingNode, String s) {
        return workingNode.value.compareTo(s) > 0 ? workingNode.left : workingNode.right;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean contains(String s) {
        if (root == null)
            return false;

        return recurisveContains(root, s);
    }

    private boolean recurisveContains(Node root, String s) {
        if (root == null)
            return false;

        if (root.value.equals(s))
            return true;

        if (root.value.compareTo(s) > 0)
            return recurisveContains(root.left, s);

        return recurisveContains(root.right, s);
    }

    @Override
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
    @Override
    public String toStringInOrder() {
        return toStringInOrderRecursive(root);
    }

    private String toStringInOrderRecursive(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalPreSpace = currentNode.left == null ? "" : " ";
        String optionalPostSpace = currentNode.right == null ? "" : " ";

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
    @Override
    public String toStringPreOrder() {
        return toStringPreOrderRevcursivce(root);
    }

    private String toStringPreOrderRevcursivce(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = currentNode == root ? "" : " ";

        return optionalSpace
                + currentNode.value
                + toStringPreOrderRevcursivce(currentNode.left)
                + toStringPreOrderRevcursivce(currentNode.right);
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
    @Override
    public String toStringPostOrder() {
        return toStringPostOrderRecursive(root);
    }

    private String toStringPostOrderRecursive(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = currentNode == root ? "" : " ";

        return toStringPostOrderRecursive(currentNode.left)
                + toStringPostOrderRecursive(currentNode.right)
                + currentNode.value
                + optionalSpace;
    }
}
