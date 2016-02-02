/**
 * This is an AVL Set. It is a set implemented with a self balancing binary search tree.
 *
 * @Author Nathan Flint
 * 2 February 2016
 *
 */
public class AVLSet extends BSTSet implements StringSet_Improved, StringSet_Check  {
    // collection of rotation types
    private Rotation[] rotations;

    /**
     * Constructor initializes class
     */
    public AVLSet() {
        numberOfNodes = 0;
        root = null;
        DELIMITER = ", ";
        rotations = new Rotation[] {
                new LeftRotation(),
                new RightRotation(),
                new LeftRightRotation(),
                new RightLeftRotation()
        };
    }

    /**
     * Rebalances the given node if it is unbalanced.
     * @param parent The parent node of the node to be rebalanced
     * @param node The node that will be checked for balance and then rebalanced if needed.
     */
    @Override
    protected void rebalanceIfNeeded(Node parent, Node node) {
        for (Rotation rotation : rotations)
            if (rotation.isNeeded(node)) {
                rotation.execute(parent, node);
                return;
            }
    }

    //Identifies is the given node's children are equal height
    private boolean nodeIsUnbalanced(Node node) {
        return Math.abs(Node.getNodeHeight(node.left) - Node.getNodeHeight(node.right)) > 1;
    }

    // Identifies if the given node's right child has greater height than its left child.
    private boolean rightChildIsLonger(Node node) {
        if (node == null) return false;

        int rightHeight = Node.getNodeHeight(node.right);
        int leftHeight = Node.getNodeHeight(node.left);
        return rightHeight > leftHeight;
    }

    // Identifies if the given node's left child has greater height than its right child.
    private boolean leftChildIsLonger(Node node) {
        if (node == null) return false;

        int rightHeight = Node.getNodeHeight(node.right);
        int leftHeight = Node.getNodeHeight(node.left);
        return leftHeight > rightHeight;
    }

    // Identifies if the given node's child are equal height. Null children have 0 height.
    private boolean childrenAreEqualHeight(Node node) {
        if (node == null)
            return false;
        return Node.getNodeHeight(node.left) == Node.getNodeHeight(node.right);
    }

    // Executes a left rotation on the given node, and updates parent node reference.
    private void rotateLeft(Node parent, Node rotatingNode) {
        Node temp = rotatingNode.right;
        rotatingNode.right = temp.left;
        temp.left = rotatingNode;

        replaceChildOfParent(parent, rotatingNode, temp);
        Node.updateHeight(rotatingNode);
        Node.updateHeight(temp);
        Node.updateHeight(parent);
    }

    // Executes a right rotation on the given node, and updates parent node reference.
    private void rotateRight(Node parent, Node rotatingNode) {
        Node temp = rotatingNode.left;
        rotatingNode.left = temp.right;
        temp.right = rotatingNode;

        replaceChildOfParent(parent, rotatingNode, temp);
        Node.updateHeight(rotatingNode);
        Node.updateHeight(temp);
        Node.updateHeight(parent);
    }

    /**
     * Composes process for Left-Right rotations
     */
    private class LeftRightRotation implements Rotation {
        /**
         * Determines if this kind of rotation is needed
         * @param node The node that will be checked
         * @return if the given node needs this kind of rotation
         */
        @Override
        public boolean isNeeded(Node node) {
            boolean rightGrandChildIsLonger = rightChildIsLonger(node.left);

            return nodeIsUnbalanced(node)
                    && leftChildIsLonger(node)
                    && rightGrandChildIsLonger;
        }

        /**
         * Executes this rotation.
         * @param parent The Parent node of the node that will be rotated
         * @param rotatingNode The node where the rotation will take place
         */

        @Override
        public void execute(Node parent, Node rotatingNode) {
            rotateLeft(rotatingNode, rotatingNode.left);
            rotateRight(parent, rotatingNode);
        }
    }

    /**
     * Composes process for Right rotations
     */
    private class RightRotation implements Rotation {
        /**
         * Determines if this kind of rotation is needed
         * @param node The node that will be checked
         * @return if the given node needs this kind of rotation
         */
        @Override
        public boolean isNeeded(Node node) {
            boolean leftGrandChildIsLongerOrEqualToRightGrandChild = leftChildIsLonger(node.left)
                    || childrenAreEqualHeight(node.left);

            return nodeIsUnbalanced(node)
                    && leftChildIsLonger(node)
                    && leftGrandChildIsLongerOrEqualToRightGrandChild;
        }

        /**
         * Executes this rotation.
         * @param parent The Parent node of the node that will be rotated
         * @param rotatingNode The node where the rotation will take place
         */

        @Override
        public void execute(Node parent, Node rotatingNode) {
            rotateRight(parent, rotatingNode);
        }
    }

    /**
     * Composes process for Left rotations
     */
    private class LeftRotation implements Rotation {
        /**
         * Determines if this kind of rotation is needed
         * @param node The node that will be checked
         * @return if the given node needs this kind of rotation
         */
        @Override
        public boolean isNeeded(Node node) {
            boolean rightGrandChildIsLongerOrEqualToLeftGrandChild = rightChildIsLonger(node.right)
                    || childrenAreEqualHeight(node.right);

            return nodeIsUnbalanced(node)
                    && rightChildIsLonger(node)
                    && rightGrandChildIsLongerOrEqualToLeftGrandChild;
        }

        /**
         * Executes this rotation.
         * @param parent The Parent node of the node that will be rotated
         * @param rotatingNode The node where the rotation will take place
         */

        @Override
        public void execute(Node parent, Node rotatingNode) {
            rotateLeft(parent, rotatingNode);
        }
    }

    /**
     * Composes process for Right-Left rotations
     */
    private class RightLeftRotation implements Rotation {
        /**
         * Determines if this kind of rotation is needed
         * @param node The node that will be checked
         * @return if the given node needs this kind of rotation
         */
        @Override
        public boolean isNeeded(Node node) {
            boolean leftGrandChildIsLonger = leftChildIsLonger(node.right);

            return nodeIsUnbalanced(node)
                    && rightChildIsLonger(node)
                    && leftGrandChildIsLonger;
        }

        /**
         * Executes this rotation.
         * @param parent The Parent node of the node that will be rotated
         * @param rotatingNode The node where the rotation will take place
         */
        @Override
        public void execute(Node parent, Node rotatingNode) {
            rotateRight(rotatingNode, rotatingNode.right);
            rotateLeft(parent, rotatingNode);
        }
    }

    /**
     * Abstract class that all rotations inherit from.
     * Also contains sub-processes that child classes will use to compose the process.
     */
    private interface Rotation {
        /**
         * Determines if this kind of rotation is needed
         * @param node The node that will be checked
         * @return if the given node needs this kind of rotation
         */
        boolean isNeeded(Node node);

        /**
         * Executes this rotation.
         * @param parent The Parent node of the node that will be rotated
         * @param rotatingNode The node where the rotation will take place
         */
        void execute(Node parent, Node rotatingNode);
    }
}
