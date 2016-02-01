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
        numberOfNodes = 0;
        root = null;
        DELINIATOR = ", ";
        rotations = new Rotation[] {
                new LeftRotation(),
                new RightRotation(),
                new LeftRightRotation(),
                new RightLeftRotation()
        };
    }

    @Override
    protected void rebalanceIfNeeded(Node parent, Node node) {
        for (Rotation rotation : rotations)
            if (rotation.isNeeded(node)) {
                rotation.execute(parent, node);
                return;
            }
    }

    // Recursively finds and then removes the given string from the given root node.
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
