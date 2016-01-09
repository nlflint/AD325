import java.util.Arrays;

/**
 * Created by nate on 1/9/16.
 */
public class ArrayStack implements NumberStack {

    private int currentSize;
    private double[] numbers;

    public ArrayStack() {
        currentSize = 0;
        numbers = new double[100];
    }
    @Override
    public void push(double number) {
        if (stackIsAtMaximumCapacity())
            increaseCapacity();

        numbers[currentSize] = number;
        currentSize++;
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
        ValidateStackContainsItems();

        currentSize--;
        return numbers[currentSize];
    }

    @Override
    public double peek() {
        ValidateStackContainsItems();

        return numbers[currentSize - 1];
    }

    private void ValidateStackContainsItems() {
        if (isEmpty())
            throw new IllegalStateException();
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }
}
