import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * Tests for BSTSet
 */
public class BSTSetTests {
    @Test
    public void add_WhenAddingStringToNewSet_ThenStringIsAdded() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        boolean wasAdded = set.add("blah");

        // Assert
        assertTrue(wasAdded);
    }

    @Test
    public void add_WhenAddingStringASecondTime_ThenStringIsNotAdded() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        set.add("blah");
        boolean wasAdded = set.add("blah");

        // Assert
        assertFalse(wasAdded);
    }

    @Test
    public void add_WhenAddingTwoDifferentString_ThenStringsAreAdded() {
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
    public void contains_WhenStringAdded_ThenSetContainsString() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        set.add("blah");

        // Assert
        String expectedContains = "blah";
        assertTrue(set.contains(expectedContains));
    }

    @Test
    public void contains_WhenTwoStringsAdded_ThenSetContainsBothString() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("blah");
        set.add("blah1");

        // Act
        boolean containsFirst = set.contains("blah");
        boolean containsSecond = set.contains("blah1");

        // Assert
        assertTrue(containsFirst);
        assertTrue(containsSecond);
    }

    @Test
    public void contains_DoesNotRelyOnReferenceEquality() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("blah");

        // Act
        boolean contains = set.contains("bl" + "ah");

        // Assert
        assertTrue(contains);
    }

    @Test
    public void add_AddingNullString_ThrowsException() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act & assert
        try {
            set.add(null);
        } catch (IllegalArgumentException ex) {
            assertEquals("Given string is null. Cannot add null string!", ex.getMessage());
            return;
        }

        fail();
    }

    @Test
    public void contains_WhenCallingContainsWithNullString_ThenExceptionIsThrown() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act & assert
        try {
            set.contains(null);
        } catch (IllegalArgumentException ex) {
            assertEquals("Given string is null. Cannot identify if set contains a null string!", ex.getMessage());
            return;
        }

        fail();
    }

    @Test
    public void contains_WhenManyStringsAdded_ThenSetContainsAllTheStrings() {
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

    // Creates 100 strings and then randomly shuffles them
    private String[] CreateRandomStringDataSet() {
        String[] testData = new String[100];
        for (int i = 0; i < testData.length; i++) {
            testData[i] = "FredBob" + i;
        }
        shuffleArray(testData);

        return testData;
    }

    // Shuffles the array
    static void shuffleArray(String[] strings)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = strings.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);


            String temp = strings[index];
            strings[index] = strings[i];
            strings[i] = temp;
        }
    }

    @Test
    public void clear_WhenClearingSetThatContainsItems_ThenSetDoesNotContainItems() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("asdf");
        set.add("qwer");
        set.clear();

        // Act
        boolean containsFirst = set.contains("asdf");
        boolean containsSecond = set.contains("qwer");
        boolean isEmpty = set.isEmpty();

        // Assert
        assertFalse(containsFirst);
        assertFalse(containsSecond);
        assertTrue(isEmpty);
    }

    @Test
    public void clear_WhenClearingAnEmptySet_ThenSetIsEmpty() {
        // Arrange
        BSTSet set = new BSTSet();
        set.clear();

        // Act
        boolean isEmpty = set.isEmpty();

        // Assert
         assertTrue(isEmpty);
    }

    @Test
    public void isEmpty_GivenNewSet_ThenSetIsEmpty() {
        // Arrange
        BSTSet set = new BSTSet();


        // Act
        set.clear();
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void isEmpty_WhenSetContainsAnItem_ThenSetIsNotEmpty() {
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
    public void isEmpty_WhenRemovingLastItem_ThenSetIsEmpty() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("asdf");

        // Act
        set.remove("asdf");
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void size_WhenSetContains100Items_ThenSizeIs100() {
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
    public void size_WhenClearingSet_ThenSizeIsZero() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("Blah");
        set.clear();

        // Act
        int size = set.size();

        // Assert
        assertEquals(0, size);
    }

    @Test
    public void size_WhenRemovingAnItem_ThenSizeIsDecremented() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("one");
        set.add("two");
        set.add("three");
        set.remove("two");

        // Act
        int size = set.size();

        // Assert
        assertEquals(2, size);
    }

    @Test
    public void remove_WhenRemovingLeaf_ThenSetDoesNotContainLeaf() {
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
    public void remove_RemovingAnItemThatDoesNotExist_ReturnsFalse() {
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
    public void remove_WhenRemovingItemWithOnlyLeftChild_ThenItemIsRemovedAndSetContainsChild() {
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
    public void remove_WhenRemovingItemWithOnlyRightChild_ThenItemIsRemovedAndSetContainsChild() {
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
    public void remove_WhenRemovingItemWithTwoChildren_ThenItemIsRemovedAndSetContainsChildren() {
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

        assertFalse(containsRemovedItem);
        assertTrue(containsChildren);

    }

    @Test
    public void remove_WhenRemovingItemThatCausesCascadingRemove_ThenOnlyOneItemIsRemoved() {
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
    public void remove_WhenRemovingRootNodeWithTwoChildren_ThenOnlyRootNodeIsRemoved() {
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
    public void contains_WhenRemovingANullString_ThenExceptionIsThrown() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act & assert
        try {
            set.remove(null);
        } catch (IllegalArgumentException ex) {
            assertEquals("Given string is null. Cannot remove a null string!", ex.getMessage());
            return;
        }

        fail();
    }

    @Test
    public void remove_WhenRemovingRootNodeWithOneChild_ThenOnlyRootNodeIsRemoved() {
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
    public void remove_WhenRemovingRandomItemFromHugeSet_ThenOnlyRandomItemIsRemoved() {
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
    public void remove_WhenRemovingItem_ThenRemoveDoesNotDependOnReferenceEquality() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("AAA");
        set.add("BBB");
        set.remove("A" + "A" + "A");

        // Act
        boolean containsRemovedItem = set.contains("AAA");
        boolean containsChild = set.contains("BBB");

        // Assert
        assertTrue(containsChild);
        assertFalse(containsRemovedItem);
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
    public void toStringInOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        String emptyResult = set.toStringInOrder();

        // Assert
        assertEquals("", emptyResult);

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
    public void toStringPreOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        String emptyResult = set.toStringPreOrder();

        // Assert
        assertEquals("", emptyResult);

    }

    @Test
    public void toStringPostOrder_WhenGettingPostOrderString_ThenItemsAreInPostOrder() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("MMM");
        set.add("YYY");
        set.add("AAA");
        set.add("ZZZ");
        set.add("PPP");
        set.add("OOO");
        set.add("UUU");
        set.add("SSS");
        set.add("RRR");

        // Act
        String postOrderResult = set.toStringPostOrder();

        // Assert
        assertEquals("AAA OOO RRR SSS UUU PPP ZZZ YYY MMM", postOrderResult);

    }

    @Test
    public void toStringPostOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        BSTSet set = new BSTSet();

        // Act
        String emptyResult = set.toStringPostOrder();

        // Assert
        assertEquals("", emptyResult);

    }
}
