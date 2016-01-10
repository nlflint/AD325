/**
 * Created by nate on 1/9/16.
 */
public class LinkedStack implements NumberStack {
    private LinkedStackNode head;

    public LinkedStack() {

    }
    @Override
    public void push(double number) {
        head = new LinkedStackNode(number, head);
    }

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

    @Override
    public double peek() {
        VerifyStackIsNotEmpty();
        return head.value;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        return surroundWithSquareBrackets(buildOutput());
    }

    private static String surroundWithSquareBrackets(String string) {
        return "[" + string + "]";
    }

    public String buildOutput() {
        if (isEmpty())
            return "";

        String output = String.valueOf(head.value);

        for (LinkedStackNode currentNode = head.next; currentNode != null; currentNode = currentNode.next) {
            output += " " + String.valueOf(currentNode.value);
        }
        return output;
    }
}
