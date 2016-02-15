package ad325.data_structres.tests;

import ad325.data_structures.PriorityQueue;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * Created by nate on 2/6/16.
 */
public class PriorityQueueTests {
    @Test
    public void add_WhenAddingOneItem_ThenItIsRemovedNext() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        add(queue, "asdf", 1);
        PriorityString result = queue.remove();

        // assert
        assertEquals("asdf", result.Value);
    }

    private boolean add(PriorityQueue<PriorityString> queue, String value, int priority) {
        return queue.add(new PriorityString(value, priority));
    }

    private PriorityQueue<PriorityString> buildMinHeapPriorityQueue() {
        return new PriorityQueue<PriorityString>(new MinHeapComparator());
    }

    @Test
    public void add_WhenAddingTwoItemsWithDifferentPriorities_ThenRemoveReturnsHighestPriority() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        add(queue, "first", 1);
        add(queue, "second", 2);
        PriorityString result = queue.remove();

        // assert
        assertEquals("first", result.Value);
    }

    @Test
    public void add_WhenAddingTwoItemsWithDifferentPriorities_ThenSecondCalledToRemoveReturnsLowestPriority() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        add(queue, "first", 1);
        add(queue, "second", 2);
        queue.remove();
        PriorityString result = queue.remove();

        // assert
        assertEquals("second", result.Value);
    }

    @Test
    public void add_WhenAddingSecondItemWithHigherPriority_ThenRemoveReturnsSecondItem() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        add(queue, "first", 10);
        add(queue, "second", 2);
        PriorityString result = queue.remove();

        // assert
        assertEquals("second", result.Value);
    }

    @Test
    public void add_WhenAddingSeveralItemsOutOfPriorityOrder_ThenItemsAreRemovedInPriorityOrder() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        add(queue, "first", 100);
        add(queue, "second", 20);
        add(queue, "third", 70);
        add(queue, "fourth", 10);
        add(queue, "fifth", 5);
        add(queue, "sixth", 50);
        PriorityString one = queue.remove();
        PriorityString two = queue.remove();
        PriorityString three = queue.remove();
        PriorityString four = queue.remove();
        PriorityString five = queue.remove();
        PriorityString six = queue.remove();

        // assert
        assertEquals("fifth", one.Value);
        assertEquals("fourth", two.Value);
        assertEquals("second", three.Value);
        assertEquals("sixth", four.Value);
        assertEquals("third", five.Value);
        assertEquals("first", six.Value);
    }

    @Test
    public void add_WhenAddingNullString_ThenExceptionIsThrown() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        try {
            queue.add(null);
            fail();
        } catch (IllegalArgumentException ex) {}
    }

    @Test
    public void add_WhenAddingSomething_ThenTrueIsReturned() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // Act
        boolean result = add(queue, "asdf", 10);

        // Assert
        assertTrue(result);
    }

    @Test
    public void minHeap_largeScaleTest() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        List<PriorityString> values = createNItemsWithRandomUniquePriority(10000);

        for(PriorityString value : values)
                queue.add(value);

        // assert
        int lastPriority = Integer.MIN_VALUE;
        while(queue.size() > 0) {
            PriorityString value = queue.remove();
            int priority = value.Priority;
            assertTrue(priority > lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void maxHeap_largeScaleTest() {
        // arrange
        PriorityQueue<PriorityString> queue = new PriorityQueue<PriorityString>(new MaxHeapComparator());

        // act
        List<PriorityString> values = createNItemsWithRandomUniquePriority(10000);

        for(PriorityString value : values)
            queue.add(value);

        // assert
        int lastPriority = Integer.MAX_VALUE;
        while(queue.size() > 0) {
            PriorityString value = queue.remove();
            int priority = value.Priority;
            assertTrue(priority < lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void SixteenAry_largeScaleTest() {
        // arrange
        PriorityQueue<PriorityString> queue = new PriorityQueue<PriorityString>(new MaxHeapComparator(), 16);

        // act
        List<PriorityString> values = createNItemsWithRandomUniquePriority(10000);

        for(PriorityString value : values)
            queue.add(value);

        // assert
        int lastPriority = Integer.MAX_VALUE;
        while(queue.size() > 0) {
            PriorityString value = queue.remove();
            int priority = value.Priority;
            assertTrue(priority < lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void TwoAry_largeScaleTest() {
        // arrange
        PriorityQueue<PriorityString> queue = new PriorityQueue<PriorityString>(new MaxHeapComparator(), 2);

        // act
        List<PriorityString> values = createNItemsWithRandomUniquePriority(10000);

        for(PriorityString value : values)
            queue.add(value);

        // assert
        int lastPriority = Integer.MAX_VALUE;
        while(queue.size() > 0) {
            PriorityString value = queue.remove();
            int priority = value.Priority;
            assertTrue(priority < lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void nAry_WhenConstructingQueueWithNaryLessThan2_ThenExceptionIsThrown() {
        try {
            PriorityQueue<PriorityString> queue = new PriorityQueue<PriorityString>(new MaxHeapComparator(), 1);
            fail();
        } catch (IllegalArgumentException ex) {

        }
    }

    @Test
    public void nAry_WhenConstructingQueueWithNaryGreaterThan16_ThenExceptionIsThrown() {
        try {
            PriorityQueue<PriorityString> queue = new PriorityQueue<PriorityString>(new MaxHeapComparator(), 17);
            fail();
        } catch (IllegalArgumentException ex) {

        }
    }

    @Test
    public void nAry_WhenConstructingQueueBetween2And16_ThenNoExceptionThrown() {
        PriorityQueue<PriorityString> queue;
        for (int i = 2; i < 17; i++)
             queue = new PriorityQueue<PriorityString>(new MinHeapComparator());
    }

    @Test
    public void remove_WhenRemovingSomethingFromEmptyQueue_ThenExceptionIsThrown() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // Act & Assert
        try {
            queue.remove();
            fail();
        } catch (IllegalStateException ex) {}
    }

    @Test
    public void size_WhenAddingItem_ThenSizeIsIncreasedByOne() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        int sizeBefore = queue.size();

        // Act
        add(queue, "asdf", 1);
        int sizeAfter = queue.size();

        // Assert
        assertEquals(0, sizeBefore);
        assertEquals(1, sizeAfter);
    }

    @Test
    public void size_WhenRemoveItem_ThenSizeIsDecreasedByOne() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        add(queue, "asdf", 1);
        int sizeBefore = queue.size();

        // Act
        queue.remove();
        int sizeAfter = queue.size();

        // Assert
        assertEquals(1, sizeBefore);
        assertEquals(0, sizeAfter);
    }

    @Test
    public void nAry_SmallScale4AryTest() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        for (int i = 20; i >= 0; i--) {
            String word = Integer.toString(i);
            add(queue, word, i);
        }

        // Act & Assert
        int lastPriority = Integer.MIN_VALUE;
        while(queue.size() > 0) {
            PriorityString value = queue.remove();
            int priority = value.Priority;
            assertTrue(priority > lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void duplicate_priorities() {
        // arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

        // act
        add(queue, "1", 1);
        add(queue, "1", 1);
        add(queue, "1", 1);
        add(queue, "1", 1);
        add(queue, "20", 20);
        add(queue, "20", 20);
        add(queue, "20", 20);
        add(queue, "20", 20);
        add(queue, "8", 8);
        add(queue, "8", 8);
        add(queue, "9", 9);
        add(queue, "9", 9);
        add(queue, "20", 20);
        add(queue, "4", 4);
        add(queue, "4", 4);
        add(queue, "4", 4);
        add(queue, "40", 40);
        add(queue, "40", 40);
        add(queue, "100", 100);
        add(queue, "65", 65);
        add(queue, "65", 65);

        int lastPriority = Integer.MIN_VALUE;
        while(queue.size() > 0) {
            PriorityString value = queue.remove();
            int priority = value.Priority;
            assertTrue(priority >= lastPriority);
            lastPriority = priority;
        }
    }

    @Test
    public void peek_WhenThereIsSeomthingOnTheQueue_ThenPeekReturnsTheThing() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        add(queue, "asdf", 1);

        // Act & Assert
        assertEquals("asdf", queue.peek().Value);
    }

    @Test
    public void peek_WhenThereIsSomethingOnTheQueue_ThenPeekDoesNotRemoveTheThing() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        add(queue, "asdf", 1);

        // Act & Assert
        assertEquals("asdf", queue.peek().Value);
        assertEquals(1, queue.size());
        assertEquals("asdf", queue.remove().Value);
        assertEquals(0, queue.size());
    }

    @Test
    public void peek_WhenThereNothingOnTheQueue_ThenPeekThrowsAnException() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();

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
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        add(queue, "asdf", 1);
        add(queue, "qwer", 2);

        // Act & Assert
        queue.clear();
        assertEquals(0, queue.size());
    }

    @Test
    public void clear_AfterClearingQueue_ThenAddAndRemoveStillBehaveProperly() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        add(queue, "asdf", 1);
        add(queue, "qwer", 2);

        // Act
        queue.clear();

        // Assert
        add(queue, "a", 10);
        add(queue, "b", 1);
        add(queue, "c", 2);
        add(queue, "d", 7);
        add(queue, "e", 5);
        add(queue, "f", 9);
        assertEquals("b", queue.remove().Value);
        assertEquals("c", queue.remove().Value);
        assertEquals("e", queue.remove().Value);
        assertEquals("d", queue.remove().Value);
        assertEquals("f", queue.remove().Value);
        assertEquals("a", queue.remove().Value);
    }

    @Test
    public void toArray_WhenQueueContainsStuff_ThenToArrayReturnsInternalHeap() {
        // Arrange
        PriorityQueue<PriorityString> queue = buildMinHeapPriorityQueue();
        add(queue, "asdf", 1);
        add(queue, "qwer", 2);

        // Act
        Object[] values = queue.toArray();

        // Assert
        assertEquals("asdf", ((PriorityString)values[0]).Value);
        assertEquals("qwer", ((PriorityString)values[1]).Value);

    }

    private List<PriorityString> createNItemsWithRandomUniquePriority(int numberOfItmes) {
        ArrayList<PriorityString> list = new ArrayList<PriorityString>();

        for(int i = 0; i < numberOfItmes; i++)
            list.add(new PriorityString(Integer.toString(i), i));

        return list;
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

    private class PriorityString {
        private final int Priority;
        private final String Value;

        private PriorityString(String value, int priority) {
            Priority = priority;
            Value = value;
        }
    }

    private class MinHeapComparator implements Comparator<PriorityString> {
        @Override
        public int compare(PriorityString o1, PriorityString o2) {
            return o1.Priority - o2.Priority;
        }
    }

    private class MaxHeapComparator implements Comparator<PriorityString> {
        @Override
        public int compare(PriorityString o1, PriorityString o2) {
            return o2.Priority - o1.Priority;
        }
    }
}
