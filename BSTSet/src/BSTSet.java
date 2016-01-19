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

        if (workingNode.value.equals(s)) {
            doRemove(lastNode, workingNode);
            return true;
        }

        if (workingNode.value.compareTo(s) > 0)
            return recursiveRemove(workingNode, workingNode.left, s);

        return recursiveRemove(workingNode, workingNode.right, s);

    }

    private void doRemove(Node lastNode, Node removingNode) {
        //if (removingNode.left == null && removingNode.right == null)

        boolean isRightNode = false;
        if (lastNode.right == removingNode)
            isRightNode = true;


//        {
//
//            if (removingNode.left != null && removingNode.right == null) {
//                lastNode.right = removingNode.left;
//            }
//            else if (removingNode.left == null && removingNode.right != null)  {
//                lastNode.right = removingNode.right;
//            }
//        }
//        else {
//            lastNode.left = removingNode.right;
//        }

        return;
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

    @Override
    public String toStringInOrder() {
        return null;
    }

    @Override
    public String toStringPreOrder() {
        return null;
    }

    @Override
    public String toStringPostOrder() {
        return null;
    }
}
