import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by nate on 1/9/16.
 */
public class ArrayStackTests {
    private final double tolerance = 0.0000000000001;

    @Test
    public void IsEmpty_WhenANewStackIsCreate_ThenItIsEmpty() {
        // Arrange
        NumberStack stack = CreateStack();

        // Assert
        assertTrue(stack.isEmpty());
    }

    @Test
    public void IsEmpty_WhenItemsArePushedOntoStack_ThenItIsNotEmpty() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        stack.push(1);
        stack.push(5);
        stack.push(90);

        // Assert
        assertFalse(stack.isEmpty());
    }

    @Test
    public void IsEmpty_WhenLastItemIsPopped_ThenItIsEmpty() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        stack.push(1);
        stack.pop();

        // Assert
        assertTrue(stack.isEmpty());
    }

    @Test
    public void IsEmpty_WhenAllButLastItemArePopped_ThenItIsNotEmpty() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        stack.push(1);
        stack.push(5);
        stack.pop();

        // Assert
        assertFalse(stack.isEmpty());
    }

    @Test
    public void Pop_WhenPoppingAnItem_ThenPoppedItemMatchesLastPushedItem() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        stack.push(1);
        stack.push(5);
        double poppedValue = stack.pop();

        // Assert
        assertEquals(5, poppedValue, tolerance);
    }

    @Test
    public void Pop_WhenPoppingItems_ThenItemsArePoppedInReverseOfPushedOrder() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        stack.push(1);
        stack.push(5);
        double firstPop = stack.pop();
        double secondPop = stack.pop();

        // Assert
        assertEquals(5, firstPop, tolerance);
        assertEquals(1, secondPop, tolerance);
    }

    @Test
    public void Pop_WhenPoppingAnEmptyStack_ThenIllegalStateExceptionIsThrown() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        try {
            double poppedValue = stack.pop();
        } catch (IllegalStateException exceptionn) {
            return;
        }

        fail();
    }

    @Test
    public void Pop_WhenPushingThenPopping1000000Items_ThenAllPoppedItemsMatchPushedItems() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        IntStream
                .rangeClosed(1, 1000000)
                .forEachOrdered(x -> stack.push(x));

        // Assert
        IntStream
                .rangeClosed(1000000, 1)
                .forEachOrdered(x -> assertEquals(x, stack.pop(), tolerance));
    }

    @Test
    public void Peek_WhenPeeking_ThenReturnedValueIsLastItemPushedAndNoItemsArePopped() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        stack.push(100);
        double peekedValue = stack.peek();
        boolean wasEmpty = stack.isEmpty();
        double poppedValue = stack.pop();

        // Assert
        assertEquals(100, peekedValue, tolerance);
        assertFalse(wasEmpty);
        assertEquals(100, poppedValue, tolerance);
        assertTrue(stack.isEmpty());
    }

    @Test
    public void Peek_WhenPeekingAnEmptyStack_ThenIllegalStateExceptionIsThrown() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        try {
            double peekedValue = stack.peek();
        } catch (IllegalStateException exceptionn) {
            return;
        }

        fail();
    }

    @Test
    public void ToString_GivenArrayHasNumbersOneThroughTen_ThenToStringOutputsValuesAsString() {
        // Arrange
        NumberStack stack = CreateStack();

        // Act
        IntStream
                .rangeClosed(1, 10)
                .forEachOrdered(x -> stack.push(x));

        // Assert
        String expectedOutput = "[10.0 9.0 8.0 7.0 6.0 5.0 4.0 3.0 2.0 1.0]";
        assertEquals(expectedOutput, stack.toString());
    }

    @Test
    public void ToString_GivenArrayIsEmpty_ThenOnlyBracketsReturned() {
        // Arrange
        NumberStack stack = CreateStack();

        // Assert
        String expectedOutput = "[]";
        assertEquals(expectedOutput, stack.toString());
    }

    public NumberStack CreateStack() {
        return new ArrayStack();
    }
}
