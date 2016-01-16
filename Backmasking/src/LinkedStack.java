/**
 * This class implements the Stack ADT using a linked list.
 */
public class LinkedStack implements NumberStack {
    private LinkedStackNode head;

    /**
     * Puts the given value on the top of the stack
     * @param number value to put on the stack
     */
    @Override
    public void push(double number) {
        head = new LinkedStackNode(number, head);
    }

    /**
     * Removes and returns the last item put on the stack.
     * @return value of the item removed from the stack.
     */
    @Override
    public double pop() {
        VerifyStackIsNotEmpty();

        LinkedStackNode poppedValue = head;
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
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Gets the value from the top of the stack without removing it.
     * @return the value at the top of the stack
     */
    @Override
    public double peek() {
        VerifyStackIsNotEmpty();
        return head.value;
    }

    /**
     * returns a string showing all the values in the stack. Topmost items on the left.
     * @return a string with all the values from the stack
     */
    @Override
    public String toString() {
        return surroundWithSquareBrace(concatNumbers());
    }

    private static String surroundWithSquareBrace(String string) {
        return "[" + string + "]";
    }

    public String concatNumbers() {
        if (isEmpty())
            return "";

        String output = String.valueOf(head.value);

        for (LinkedStackNode currentNode = head.next; currentNode != null; currentNode = currentNode.next) {
            output += " " + String.valueOf(currentNode.value);
        }
        return output;
    }
}
