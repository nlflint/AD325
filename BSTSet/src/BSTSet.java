import java.util.Stack;

/**
 * Created by nate on 1/16/16.
 */
public class BSTSet implements StringSet_Plus {
    private Node root;

    @Override
    public StringIterator iteratorInOrder() {
        return new InOrderIterator(this);
    }

    @Override
    public StringIterator iteratorPreOrder() {
        return new PreOrderIterator(this);
    }

    @Override
    public StringIterator iteratorPostOrder() {
        return new PostOrderIterator(this);
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

    private class InOrderIterator implements StringIterator {
        private Stack<Node> nodes;
        private BSTSet bstSet;
        private Node lastNode;

        public InOrderIterator(BSTSet set) {
            bstSet = set;
            nodes = new Stack<Node>();
            fillStackUntilNextNode(set.root);
        }

        private void fillStackUntilNextNode(Node startingNode) {
            while(startingNode != null) {
                nodes.push(startingNode);
                startingNode = startingNode.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !nodes.isEmpty();
        }

        @Override
        public String next() {
            if (!hasNext())
                throw new IllegalStateException("There are no more items");

            Node nextNode = nodes.pop();
            fillStackUntilNextNode(nextNode.right);

            lastNode = nextNode;
            return nextNode.value;
        }

        @Override
        public void remove() {
            if (lastNode != null)
                bstSet.remove(lastNode.value);
        }
    }

    private class PreOrderIterator implements StringIterator {
        private BSTSet bstSet;
        private Stack<Node> nodes;
        private Node lastNode;

        public PreOrderIterator(BSTSet set) {
            bstSet = set;

            nodes = new Stack<Node>();
            nodes.push(set.root);
        }

        /**
         * Returns true if the iteration has more elements.
         * (In other words, returns true if next() would
         * return an element rather than throwing an
         * exception.)
         *
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return !nodes.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws IllegalStateException if the iteration
         *                               has no more elements
         */
        @Override
        public String next() {
            if (!hasNext())
                throw new IllegalStateException("There are no more items");

            Node nextNode = nodes.pop();

            if (nextNode.right != null)
                nodes.push(nextNode.right);

            if (nextNode.left != null)
                nodes.push(nextNode.left);

            lastNode = nextNode;
            return nextNode.value;
        }

        /**
         * Removes from the underlying collection the last
         * element returned by this iterator (optional
         * operation). This method can be called only once
         * per call to next(). The behavior of an iterator
         * is unspecified if the underlying collection is
         * modified while the iteration is in progress in
         * any way other than by calling this method.
         *
         * @throws UnsupportedOperationException if the
         * remove operation is not supported by this
         * iterator
         */
        @Override
        public void remove() {
            bstSet.remove(lastNode.value);
        }
    }

    private class PostOrderIterator implements StringIterator {
        private BSTSet bstSet;
        private Stack<Node> nodes;
        private Node lastNode;

        public PostOrderIterator(BSTSet set) {
            bstSet = set;

            nodes = new Stack<Node>();
            fillStackUntilNextNode(set.root);
        }

        private void fillStackUntilNextNode(Node startingNode) {
            while(startingNode != null) {
                nodes.push(startingNode);
                if (startingNode.left != null) {
                    startingNode = startingNode.left;
                } else {
                    startingNode = startingNode.right;
                }
            }
        }

        /**
         * Returns true if the iteration has more elements.
         * (In other words, returns true if next() would
         * return an element rather than throwing an
         * exception.)
         *
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return !nodes.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws IllegalStateException if the iteration
         *                               has no more elements
         */
        @Override
        public String next() {
            if (!hasNext())
                throw new IllegalStateException("There are no more items");

            Node currentNode = nodes.pop();

            if (!nodes.isEmpty()) {
                Node parentNode = nodes.peek();
                if (parentNode.left == currentNode) {
                    fillStackUntilNextNode(parentNode.right);
                }
            }

            lastNode = currentNode;
            return currentNode.value;
        }

        /**
         * Removes from the underlying collection the last
         * element returned by this iterator (optional
         * operation). This method can be called only once
         * per call to next(). The behavior of an iterator
         * is unspecified if the underlying collection is
         * modified while the iteration is in progress in
         * any way other than by calling this method.
         *
         * @throws UnsupportedOperationException if the
         *                                       remove operation is not supported by this
         *                                       iterator
         */
        @Override
        public void remove() {
            bstSet.remove(lastNode.value);
        }
    }
}
