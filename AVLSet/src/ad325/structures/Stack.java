package ad325.structures;
/**
 * @author Nathan Flint
 * Assignment 3
 * Level: Plus
 *
 * This class implements the Stack ADT using a linked list.
 */
class Stack<T> {
    // the head of the linked list
    private StackNode<T> head;

    /**
     * Puts the given value on the top of the stack
     * @param value value to put on the stack
     */
    public void push(T value) {
        head = new StackNode<T>(value, head);
    }

    /**
     * Removes and returns the last item put on the stack.
     * @return value of the item removed from the stack.
     */
    public T pop() {
        VerifyStackIsNotEmpty();

        StackNode<T> poppedValue = head;
        head = head.next;
        return poppedValue.value;
    }

    // Validates if the stack is empty
    private void VerifyStackIsNotEmpty() {
        if (isEmpty())
            throw new IllegalStateException();
    }

    /**
     * Identifies if the stack contains any items.
     * @return true if the stack is empty
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * This is a node for the linked list
     * @param <T> The type of object this node will contain.
     */
    private class StackNode<T> {
        /**
         * The value in the node
         */
        public T value;

        /**
         * Reference to the next node in the linked list
         */
        public StackNode<T> next;

        /**
         * Constructor. Takes a value and reference to the next node in the list.
         * @param v Value that this node will hold
         * @param n refece to the next node in the linked list
         */
        public StackNode(T v, StackNode<T> n) {
            value = v;
            next = n;
        }
    }
}


