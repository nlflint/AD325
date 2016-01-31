/**
 * This class implements the Stack ADT using a linked list.
 */
class Stack<T> {
    private StackNode<T> head;

    /**
     * Puts the given value on the top of the stack
     * @param number value to put on the stack
     */
    public void push(T number) {
        head = new StackNode<T>(number, head);
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
     * Gets the value from the top of the stack without removing it.
     * @return the value at the top of the stack
     */
    public T peek() {
        VerifyStackIsNotEmpty();
        return head.value;
    }
}

class StackNode<T> {
    public T value;
    public StackNode<T> next;
    public StackNode(T v, StackNode<T> n) {
        value = v;
        next = n;
    }
}
