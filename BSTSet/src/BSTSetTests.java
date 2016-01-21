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

    @Test
    public void Remove_WhenLeafIsRemoved_ThenSetDoesNotContainItem() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("B");
        set.add("A");
        set.remove("A");

        // Act
        boolean contains = set.contains("A");

        // Assert
        assertFalse(contains);
    }

    @Test
    public void Remove_WhenRemovingItemThatDoesntExist_ThenReturnsFalse() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("B");
        set.add("A");
        set.remove("C");

        // Act
        boolean containsA = set.contains("A");
        boolean containsB = set.contains("B");

        // Assert
        assertTrue(containsA);
        assertTrue(containsB);
    }

    @Test
    public void Remove_WhenItemWithOnlyLeftEdgeIsRemove_ThenItemIsRemoveAndSetContainsChildOfItem() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("H");
        set.add("P");
        set.add("L");
        set.add("K");
        set.add("M");
        set.remove("P");

        // Act
        boolean containsRemovedItem = set.contains("P");
        boolean containsChildren = set.contains("L");
        containsChildren &= set.contains("K");
        containsChildren &= set.contains("M");


        // Assert
        assertTrue(containsChildren);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void Remove_WhenItemWithOnlyRightEdgeIsRemove_ThenItemIsRemoveAndSetContainsChildOfItem() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("Z");
        set.add("P");
        set.add("T");
        set.add("Q");
        set.add("V");
        set.remove("P");

        // Act
        boolean containsRemovedItem = set.contains("P");
        boolean containsChildren = set.contains("T");
        containsChildren &= set.contains("Q");
        containsChildren &= set.contains("V");

        // Assert
        assertTrue(containsChildren);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void Remove_WhenItemWithTwoEdgesIsRemoved_ThenItemIsRemoveAndSetContainsChildOfItem() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("H");
        set.add("P");
        set.add("T");
        set.add("V");
        set.add("U");
        set.add("M");
        set.add("K");
        set.add("N");
        set.remove("P");

        // Act
        boolean containsRemovedItem = set.contains("P");
        boolean containsChildren = set.contains("T");
        containsChildren &= set.contains("V");
        containsChildren &= set.contains("U");
        containsChildren &= set.contains("M");
        containsChildren &= set.contains("K");
        containsChildren &= set.contains("N");
        containsChildren &= set.contains("H");

        // Assert
        assertTrue(containsChildren);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void Remove_WhenRemovingItemThatCausesCascadingRemove_ThenOnlyOneItemIsRemoved() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("M");
        set.add("Y");
        set.add("Z");
        set.add("P");
        set.add("O");
        set.add("U");
        set.add("S");
        set.add("R");
        set.remove("Y");

        // Act
        boolean containsRemovedItem = set.contains("Y");
        boolean containsChildren = set.contains("M");
        containsChildren &= set.contains("Z");
        containsChildren &= set.contains("P");
        containsChildren &= set.contains("O");
        containsChildren &= set.contains("U");
        containsChildren &= set.contains("S");
        containsChildren &= set.contains("R");

        // Assert
        assertTrue(containsChildren);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void Remove_WhenRemovingRootNodeWithTwoChildren_ThenOnlyRootNodeIsRemoved() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("M");
        set.add("L");
        set.add("Z");
        set.add("P");
        set.add("A");
        set.remove("M");

        // Act
        boolean containsRemovedItem = set.contains("M");
        boolean containsChildren = set.contains("L");
        containsChildren &= set.contains("Z");
        containsChildren &= set.contains("P");
        containsChildren &= set.contains("A");

        // Assert
        assertTrue(containsChildren);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void Remove_WhenRemovingRootNodeWithOneChild_ThenOnlyRootNodeIsRemoved() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("A");
        set.add("B");
        set.add("C");
        set.remove("A");

        // Act
        boolean containsRemovedItem = set.contains("A");
        boolean containsChildren = set.contains("B");
        containsChildren &= set.contains("C");

        // Assert
        assertTrue(containsChildren);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void Add_WhenRemovingRandomItemFromHugeSet_ThenOnlyRandomItemIsRemoved() {
        // Arrange
        BSTSet set = new BSTSet();
        String[] addedFredBobs = CreateRandomStringDataSet();

        for (String fredBob : addedFredBobs)
            set.add(fredBob);

        // Act
        String[] verifyingFredBobs = CreateRandomStringDataSet();
        int itemIndexToRemove = 50;
        set.remove(verifyingFredBobs[itemIndexToRemove]);

        // Assert
        for (int i = 0; i < verifyingFredBobs.length; i++) {
            if (i == itemIndexToRemove)
                assertFalse(set.contains(verifyingFredBobs[i]));
            else
                assertTrue(set.contains(verifyingFredBobs[i]));
        }
    }

    @Test
    public void toStringInOrder_WhenGettingInOrderString_ThenItemsAreInAlphabeticalOrder() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("M");
        set.add("Y");
        set.add("A");
        set.add("Z");
        set.add("P");
        set.add("O");
        set.add("U");
        set.add("S");
        set.add("R");

        // Act
        String inOrderResult = set.toStringInOrder();

        // Assert
        assertEquals("A M O P R S U Y Z", inOrderResult);

    }

    @Test
    public void toStringPreOrder_WhenGettingPreOrderString_ThenItemsAreInPreOrder() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("M");
        set.add("Y");
        set.add("A");
        set.add("Z");
        set.add("P");
        set.add("O");
        set.add("U");
        set.add("S");
        set.add("R");

        // Act
        String preOrderResult = set.toStringPreOrder();

        // Assert
        assertEquals("M A Y P O U S R Z", preOrderResult);

    }

    @Test
    public void toStringPostOrder_WhenGettingPostOrderString_ThenItemsAreInPostOrder() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("M");
        set.add("Y");
        set.add("A");
        set.add("Z");
        set.add("P");
        set.add("O");
        set.add("U");
        set.add("S");
        set.add("R");

        // Act
        String postOrderResult = set.toStringPostOrder();

        // Assert
        assertEquals("A O R S U P Z Y M", postOrderResult);

    }
}
