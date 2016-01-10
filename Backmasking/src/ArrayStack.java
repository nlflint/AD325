import java.util.Arrays;

/**
 * Created by nate on 1/9/16.
 */
public class ArrayStack implements NumberStack {

    private int currentSize;
    private double[] numbers;

    public ArrayStack() {
        numbers = new double[100];
    }

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

    @Override
    public double pop() {
        verifyStackIsNotEmpty();
        return numbers[--currentSize];
    }

    @Override
    public double peek() {
        verifyStackIsNotEmpty();
        return numbers[currentSize - 1];
    }

    private void verifyStackIsNotEmpty() {
        if (isEmpty())
            throw new IllegalStateException();
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

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
