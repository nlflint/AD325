package ad325.data_structres.tests;

import ad325.data_structures.PrioritizedWords;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
    public void add_WhenAddingTenThousandItemsWithUniquePriority_ThenItemsAreRemovedInPriorityOrder() {
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
            int priority = values.get(value);
            assertTrue(priority > lastPriority);
            lastPriority = priority;
        }
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
    public void nAry_Full4AryTest() {
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
