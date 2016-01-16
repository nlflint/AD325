import java.util.Arrays;

/**
 * This class implements the Stack ADT using an array.
 */
public class ArrayStack implements NumberStack {

    private int currentSize;
    private double[] numbers;

    /**
     * Constructor initializes the stack
     */
    public ArrayStack() {
        numbers = new double[100];
    }

    /**
     * Puts the given value on the top of the stack
     * @param number value to put on the stack
     */
    @Override
    public void push(double number) {
        if (stackIsAtMaximumCapacity())
            increaseCapacity();
        numbers[currentSize++] = number;
    }

    private boolean stackIsAtMaximumCapacity() {
        return numbers.length == currentSize;
    }

    private void increaseCapacity() {
        int newSize = numbers.length * 2;
        numbers = Arrays.copyOf(numbers, newSize);
    }

    /**
     * Removes and returns the last item put on the stack.
     * @return value of the item removed from the stack.
     */
    @Override
    public double pop() {
        verifyStackIsNotEmpty();
        return numbers[--currentSize];
    }

    private void verifyStackIsNotEmpty() {
        if (isEmpty())
            throw new IllegalStateException();
    }

    /**
     * Identifies if the stack contains any items.
     * @return true if the stack is empty
     */
    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Gets the value from the top of the stack without removing it.
     * @return the value at the top of the stack
     */
    @Override
    public double peek() {
        verifyStackIsNotEmpty();
        return numbers[currentSize - 1];
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

    private String concatNumbers() {
        return Arrays
                .stream(numbers, 0, currentSize)
                .mapToObj(x -> String.valueOf(x))
                .reduce((first, second) -> second + " " + first)
                .orElse("");
    }
}
