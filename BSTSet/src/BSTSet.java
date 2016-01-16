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
        return false;
    }

    @Override
    public int size() {
        return 0;
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

        if (workingNode.value.compareTo(s) > 0) {
            if (workingNode.left != null)   {
                return recursiveAdd(workingNode.left, s);
            }

            workingNode.left = new Node(s);
            return true;
        }

        if (workingNode.right != null) {
            return recursiveAdd(workingNode.right, s);
        }

        workingNode.right = new Node(s);
        return true;
    }

    @Override
    public void clear() {

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
        return false;
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
