package ad325.data_structres.tests;

import ad325.data_structures.PrioritizedWords;
import ad325.data_structures.PriorityQueue;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by nate on 2/6/16.
 */
public class PrioritizedWordsTests {
    @Test
    public void add_WhenAddingOneItem_ThenItIsRemovedNext() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        queue.add("first", 1);
        String result = queue.remove();

        // assert
        assertEquals("first", result);
    }

    @Test
    public void add_WhenAddingTwoItemsWithDifferentPriorities_ThenRemoveReturnsHighestPriority() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        queue.add("first", 1);
        queue.add("second", 2);
        String result = queue.remove();

        // assert
        assertEquals("first", result);
    }

    @Test
    public void add_WhenAddingTwoItemsWithDifferentPriorities_ThenSecondCalledToRemoveReturnsLowestPriority() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        queue.add("first", 1);
        queue.add("second", 2);
        queue.remove();
        String result = queue.remove();

        // assert
        assertEquals("second", result);
    }

    @Test
    public void add_WhenAddingSecondItemWithHigherPriority_ThenRemoveReturnsSecondItem() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        queue.add("first", 10);
        queue.add("second", 2);
        String result = queue.remove();

        // assert
        assertEquals("second", result);
    }

    @Test
    public void add_WhenAddingSeveralItemsOutOfPriorityOrder_ThenItemsAreRemovedInPriorityOrder() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        queue.add("first", 100);
        queue.add("second", 20);
        queue.add("third", 70);
        queue.add("fourth", 10);
        queue.add("fifth", 5);
        queue.add("sixth", 50);
        String one = queue.remove();
        String two = queue.remove();
        String three = queue.remove();
        String four = queue.remove();
        String five = queue.remove();
        String six = queue.remove();

        // assert
        assertEquals("fifth", one);
        assertEquals("fourth", two);
        assertEquals("second", three);
        assertEquals("sixth", four);
        assertEquals("third", five);
        assertEquals("first", six);
    }

    @Test
    public void add_WhenAddingNullString_ThenExceptionIsThrown() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        try {
            queue.add(null, 100);
            fail();
        } catch (IllegalArgumentException ex) {}

    }

    @Test
    public void add_WhenAddingEmptyString_ThenExceptionIsThrown() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        try {
            queue.add("", 100);
            fail();
        } catch (IllegalArgumentException ex) {}

    }

    @Test
    public void add_WhenAddingSomething_ThenTrueIsReturned() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // Act
        boolean result = queue.add("asdf", 10);

        // Assert
        assertTrue(result);
    }

    @Test
    public void addRemove_LargeScaleStringPriorityIntegrityTest() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        Map<String, Integer> values = createNItemsWithRandomUniquePriority(10000);

        for(Map.Entry<String, Integer> pair : values.entrySet())
                queue.add(pair.getKey(), pair.getValue());

        // assert
        int lastPriority = Integer.MIN_VALUE;
        while(queue.size() > 0) {
            String value = queue.remove();
            int expectedPriority = values.get(value);
            assertTrue(expectedPriority > lastPriority);
            lastPriority = expectedPriority;
        }
    }

    @Test
    public void remove_WhenRemovingSomethingFromEmptyQueue_ThenExceptionIsThrown() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();

        // Act & Assert
        try {
            queue.remove();
            fail();
        } catch (IllegalStateException ex) {}
    }

    @Test
    public void size_WhenAddingItem_ThenSizeIsIncreasedByOne() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();
        int sizeBefore = queue.size();

        // Act
        queue.add("asdf", 1);
        int sizeAfter = queue.size();

        // Assert
        assertEquals(0, sizeBefore);
        assertEquals(1, sizeAfter);
    }

    @Test
    public void size_WhenRemoveItem_ThenSizeIsDecreasedByOne() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();
        queue.add("asdf", 1);
        int sizeBefore = queue.size();

        // Act
        queue.remove();
        int sizeAfter = queue.size();

        // Assert
        assertEquals(1, sizeBefore);
        assertEquals(0, sizeAfter);
    }

    @Test
    public void nAry_Sample4AryTest() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();
        for (int i = 20; i >= 0; i--) {
            String word = Integer.toString(i);
            queue.add(word, i);
        }

        // Act & Assert
        int lastPriority = Integer.MIN_VALUE;
        while(queue.size() > 0) {
            String value = queue.remove();
            int priority = Integer.parseInt(value);
            assertTrue(priority > lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void duplicate_priorities() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();

        // act
        queue.add("1", 1);
        queue.add("1", 1);
        queue.add("1", 1);
        queue.add("1", 1);
        queue.add("20", 20);
        queue.add("20", 20);
        queue.add("20", 20);
        queue.add("20", 20);
        queue.add("8", 8);
        queue.add("8", 8);
        queue.add("9", 9);
        queue.add("9", 9);
        queue.add("20", 20);
        queue.add("4", 4);
        queue.add("4", 4);
        queue.add("4", 4);
        queue.add("40", 40);
        queue.add("40", 40);
        queue.add("100", 100);
        queue.add("65", 65);
        queue.add("65", 65);

        int lastPriority = Integer.MIN_VALUE;
        while(queue.size() > 0) {
            String value = queue.remove();
            int priority = Integer.parseInt(value);
            assertTrue(priority >= lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void peek_WhenThereIsSeomthingOnTheQueue_ThenPeekReturnsTheThing() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();
        queue.add("asdf", 1);

        // Act & Assert
        assertEquals("asdf", queue.peek());
    }

    @Test
    public void peek_WhenThereIsSomethingOnTheQueue_ThenPeekDoesNotRemoveTheThing() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();
        queue.add("asdf", 1);

        // Act & Assert
        assertEquals("asdf", queue.peek());
        assertEquals(1, queue.size());
        assertEquals("asdf", queue.remove());
        assertEquals(0, queue.size());
    }

    @Test
    public void peek_WhenThereNothingOnTheQueue_ThenPeekThrowsAnException() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();

        // Act & Assert
        try
        {
            queue.peek();
            fail();
        } catch (IllegalStateException ex) {}
    }

    @Test
    public void clear_WhenThereIsSomethingOnTheQueue_ThenClearDeletesEverythingFromTheQueue() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();
        queue.add("asdf", 1);
        queue.add("qwer", 2);

        // Act & Assert
        queue.clear();
        assertEquals(0, queue.size());
    }

    @Test
    public void clear_AfterClearingQueue_ThenAddAndRemoveStillBehaveProperly() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();
        queue.add("asdf", 1);
        queue.add("qwer", 2);
        queue.clear();

        // Act
        queue.add("a", 10);
        queue.add("b", 1);
        queue.add("c", 2);
        queue.add("d", 7);
        queue.add("e", 5);
        queue.add("f", 9);

        // Assert
        assertEquals("b", queue.remove());
        assertEquals("c", queue.remove());
        assertEquals("e", queue.remove());
        assertEquals("d", queue.remove());
        assertEquals("f", queue.remove());
        assertEquals("a", queue.remove());
    }

    @Test
    public void toArray_WhenQueueContainsStuff_ThenToArrayReturnsInternalHeap() {
        // Arrange
        PrioritizedWords queue = new PrioritizedWords();
        queue.add("asdf", 1);
        queue.add("qwer", 2);

        // Act
        String[] values = (String[]) queue.toArray();

        // Assert
        assertThat(values, is( new String[] {"asdf", "qwer"}));

    }

    private Map<String, Integer> createNItemsWithRandomUniquePriority(int numberOfItmes) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        int[] priorities = CreateRandomlySortedStringDataSet(numberOfItmes);
        int[] strings = CreateRandomlySortedStringDataSet(numberOfItmes);

        for(int i = 0; i < numberOfItmes; i++)
            map.put(Integer.toString(strings[i]), priorities[i]);

        return map;
    }


    private int[] CreateRandomlySortedStringDataSet(int numberOfElements) {
        int[] testData = new int[numberOfElements];
        for (int i = 1; i <= testData.length; i++) {
            testData[i - 1] = i;
        }
        shuffleArray(testData);

        return testData;
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
