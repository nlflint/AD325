import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

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
        if (isEmpty())
            return "[]";

        String output = Arrays
                .stream(numbers, 0, currentSize)
                .mapToObj(x -> String.valueOf(x))
                .reduce((first, second) -> second + " " + first).get();
        return surroundWithSquareBrackets(output);
    }

    public static String surroundWithSquareBrackets(String string) {
        return "[" + string + "]";
    }
}
