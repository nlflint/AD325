/**
 * Nathan Flint
 * Assignment 2: BSTSet
 * Level: Plus
 *
 * This class implements an ADT called Set. A binary search tree is used for the implementation.
 */

import java.util.Stack;

/**
 * This is a Set implemented using a binary tree,
 * and is the PLUS version of the assignment.
 *
 * @author Nathan Flint
 */
public class BSTSet implements StringSet_Plus {
    // The root node of the binary tree
    private Node root;

    // The number of nodes in the tree
    private int numberOfNodes;

    /**
     * Constructor initializes an empty set
     */
    public BSTSet() {
        numberOfNodes = 0;
        root = null;
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
     * element is null
     */
    @Override
    public boolean add(String s) {
        if (s == null)
            throw new NullPointerException("Given string is null. Cannot add null string!");

        if (root == null) {
            root = new Node(s);
            numberOfNodes++;
            return true;
        }

        if (recursiveAdd(root, s)) {
            numberOfNodes++;
            return true;
        }
        return false;
    }

    // Recursively traverses the tree looking for the right
    // place to put the given string.
    private boolean recursiveAdd(Node workingNode, String s) {
        if (workingNode.value.equals(s))
            return false;

        Node nextNode = getNextNode(workingNode, s);

        if (nextNode != null)
            return recursiveAdd(nextNode,s);

        addStringToCorrectEdge(workingNode, s);

        return true;
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
     * @throws NullPointerException if the specified
     * element is null
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
     * @throws NullPointerException if the specified
     * element is null
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
    private void updateParentReference(Node parent, Node childToRemove, Node newChild) {
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
    @Override
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
    @Override
    public void clear() {
        root = null;
        numberOfNodes = 0;
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

    // Builds a string of tree elements recursively from the given
    // node using In-Order sequence
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
        return toStringPreOrderRecursivce(root);
    }

    // Builds a string of tree elements recursively from the given
    // node using Pre-Order sequence
    private String toStringPreOrderRecursivce(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = nodeIsRoot(currentNode) ? "" : " ";

        return optionalSpace
                + currentNode.value
                + toStringPreOrderRecursivce(currentNode.left)
                + toStringPreOrderRecursivce(currentNode.right);
    }

    // Identifies if the given node is the root node
    private boolean nodeIsRoot(Node currentNode) {
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
    @Override
    public String toStringPostOrder() {
        return toStringPostOrderRecursive(root);
    }

    // Builds a string of tree elements recursively from the given
    // node using Post-Order sequence
    private String toStringPostOrderRecursive(Node currentNode) {
        if (currentNode == null)
            return "";

        String optionalSpace = nodeIsRoot(currentNode) ? "" : " ";

        return toStringPostOrderRecursive(currentNode.left)
                + toStringPostOrderRecursive(currentNode.right)
                + currentNode.value
                + optionalSpace;
    }

    /**
     * Returns an iterator over the elements in this set.
     * The elements are returned using an in-order
     * traversal of the underlying tree.
     *
     * @return an iterator over the elements in this set
     */
    @Override
    public StringIterator iteratorInOrder() {
        return new InOrderIterator(this);
    }

    /**
     * Returns an iterator over the elements in this set.
     * The elements are returned using a pre-order
     * traversal of the underlying tree.
     *
     * @return an iterator over the elements in this set
     */
    @Override
    public StringIterator iteratorPreOrder() {
        return new PreOrderIterator(this);
    }

    /**
     * Returns an iterator over the elements in this set.
     * The elements are returned using a post-order
     * traversal of the underlying tree.
     *
     * @return an iterator over the elements in this set
     */
    @Override
    public StringIterator iteratorPostOrder() {
        return new PostOrderIterator(this);
    }

    /**
     * This iterator will traverse a BSTSet using In-Order sequence.
     */
    private class InOrderIterator implements StringIterator {
        // Stores the current path of the iterator. Top item is always the next item.
        private Stack<Node> nodes;
        // The set that this iterator is traversing
        private BSTSet bstSet;
        // Last node that was returned from Next();
        private Node lastNode;

        /**
         * Constructor for iterator
         * @param set The BSTset that this iterator will traverse
         */
        public InOrderIterator(BSTSet set) {
            bstSet = set;
            nodes = new Stack<Node>();
            fillStackUntilNextNode(set.root);
        }

        // Pushes a path of nodes onto the stack until it finds the next node.
        private void fillStackUntilNextNode(Node startingNode) {
            while(startingNode != null) {
                nodes.push(startingNode);
                startingNode = startingNode.left;
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

            Node nextNode = nodes.pop();
            fillStackUntilNextNode(nextNode.right);

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
            if (lastNode != null)
                bstSet.remove(lastNode.value);
        }
    }

    /**
     * This iterator will traverse a BSTSet in Pre-Order sequence.
     */
    private class PreOrderIterator implements StringIterator {
        // Stores the current path of the iterator. Top item is always the next item.
        private Stack<Node> nodes;
        // The set that this iterator is traversing
        private BSTSet bstSet;
        // Last node that was returned from Next();
        private Node lastNode;

        /**
         * Constructor for iterator
         * @param set The BSTset that this iterator will traverse
         */
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

    /**
     * This iterator will traverse a BSTSet in Post-Order sequence.
     */
    private class PostOrderIterator implements StringIterator {
        // Stores the current path of the iterator. Top item is always the next item.
        private Stack<Node> nodes;
        // The set that this iterator is traversing
        private BSTSet bstSet;
        // Last node that was returned from Next();
        private Node lastNode;

        /**
         * Constructor for iterator
         * @param set The BSTset that this iterator will traverse
         */
        public PostOrderIterator(BSTSet set) {
            bstSet = set;
            nodes = new Stack<Node>();
            fillStackUntilNextNode(set.root);
        }

        // Finds the next node and pushes nodes into stack along the way
        private void fillStackUntilNextNode(Node startingNode) {
            while(startingNode != null) {
                nodes.push(startingNode);

                // First, go left as much as possible
                if (startingNode.left != null) {
                    startingNode = startingNode.left;
                    continue;
                }

                // If can't go left, then try to go right with secondary preference
                startingNode = startingNode.right;
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
