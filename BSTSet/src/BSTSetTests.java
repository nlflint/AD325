import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * Created by nate on 1/16/16.
 */
public class BSTSetTests {
    @Test
    public void Add_WhenAddingStringToNewSet_ThenStringIsAdded() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        boolean wasAdded = set.add("blah");

        // Assert
        assertTrue(wasAdded);
    }

    @Test
    public void Add_WhenAddingStringASecondTime_ThenStringIsNotAdded() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        set.add("blah");
        boolean wasAdded = set.add("blah");

        // Assert
        assertFalse(wasAdded);
    }

    @Test
    public void Add_WhenAddingTwoDifferentString_ThenStringsAreAdded() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        boolean firstAdded = set.add("blah");
        boolean secondAdded = set.add("blah1");

        // Assert
        assertTrue(firstAdded);
        assertTrue(secondAdded);
    }

    @Test
    public void Add_WhenStringAdded_ThenSetContainsString() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        set.add("blah");

        // Assert
        String expectedContains = "bla" + "h";
        assertTrue(set.contains(expectedContains));
    }

    @Test
    public void Add_WhenTwoStringsAdded_ThenSetContainsBothString() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("blah");
        set.add("blah1");

        // Act
        boolean containsFirst = set.contains("bla" + "h");
        boolean containsSecond = set.contains("bla" + "h1");

        // Assert
        assertTrue(containsFirst);
        assertTrue(containsSecond);
    }

    @Test
    public void Add_WhenLotsOfStringsAdded_ThenSetContainsAllString() {
        // Arrange
        BSTSet set = new BSTSet();
        String[] addedFredBobs = CreateRandomStringDataSet();

        for (String fredBob : addedFredBobs)
                set.add(fredBob);

        // Act & Assert
        String[] verifiedFredBobs = CreateRandomStringDataSet();
        for (String fredBob : verifiedFredBobs)
            assertTrue(set.contains(fredBob));

    }

    private String[] CreateRandomStringDataSet() {
        String[] testData = new String[100];
        for (int i = 0; i < testData.length; i++) {
            testData[i] = "FredBob" + i;
        }
        shuffleArray(testData);

        return testData;
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(String[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @Test
    public void Clear_WhenClearingSetThatContainsString_ThenSetDoesNotContainStrings() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("asdf");
        set.add("qwer");
        set.clear();

        // Act
        boolean containsFirst = set.contains("asd" + "f");
        boolean containsSecond = set.contains("qwe" + "r");

        // Assert
        assertFalse(containsFirst);
        assertFalse(containsSecond);
    }

    @Test
    public void IsEmpty_GivenNewSet_ThenSetIsEmpty() {
        // Arrange
        BSTSet set = new BSTSet();


        // Act
        set.clear();
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void IsEmpty_GivenSetContainsAnItem_ThenSetIsNotEmpty() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("asdf");

        // Act
        set.clear();
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void Size_GivenSetContains100Items_ThenSetSizeIs100() {
        // Arrange
        BSTSet set = new BSTSet();
        String[] fredBobs = CreateRandomStringDataSet();
        for(String fredBob : fredBobs)
                set.add(fredBob);

        // Act
        int size = set.size();

        // Assert
        assertEquals(100, size);
    }
}
