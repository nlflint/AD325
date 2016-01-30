import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by nathanf on 1/21/2016.
 */
public class AVLSetTests {
    @Test
    public void add_WhenAddingStringToNewSet_ThenStringIsAdded() {
        // Arrange
        AVLSet set = new AVLSet();

        // Act
        boolean wasAdded = set.add("blah");

        // Assert
        assertTrue(wasAdded);
    }

    @Test
    public void add_WhenAddingStringASecondTime_ThenStringIsNotAdded() {
        // Arrange
        AVLSet set = new AVLSet();

        // Act
        set.add("blah");
        boolean wasAdded = set.add("blah");

        // Assert
        assertFalse(wasAdded);
    }

    @Test
    public void add_WhenAddingTwoDifferentString_ThenStringsAreAdded() {
        // Arrange
        AVLSet set = new AVLSet();

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
        AVLSet set = new AVLSet();

        // Act
        set.add("blah");

        // Assert
        String expectedContains = "blah";
        assertTrue(set.contains(expectedContains));
    }

    @Test
    public void contains_WhenTwoStringsAdded_ThenSetContainsBothString() {
        // Arrange
        AVLSet set = new AVLSet();
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
        AVLSet set = new AVLSet();
        set.add("blah");

        // Act
        boolean contains = set.contains("bl" + "ah");

        // Assert
        assertTrue(contains);
    }

    @Test
    public void add_AddingNullString_ThrowsException() {
        // Arrange
        AVLSet set = new AVLSet();

        // Act & assert
        try {
            set.add(null);
        } catch (NullPointerException ex) {
            assertEquals("Given string is null. Cannot add null string!", ex.getMessage());
            return;
        }

        fail();
    }

    @Test
    public void contains_WhenCallingContainsWithNullString_ThenExceptionIsThrown() {
        // Arrange
        AVLSet set = new AVLSet();

        // Act & assert
        try {
            set.contains(null);
        } catch (NullPointerException ex) {
            assertEquals("Given string is null. Cannot identify if set contains a null string!", ex.getMessage());
            return;
        }

        fail();
    }

    @Test
    public void contains_WhenManyStringsAdded_ThenSetContainsAllTheStrings() {
        // Arrange
        int numberOfItems = 100;
        AVLSet set = new AVLSet();
        String[] addedFredBobs = CreateRandomStringDataSet(numberOfItems);

        for (String fredBob : addedFredBobs)
            set.add(fredBob);

        // Act & Assert
        String[] verifiedFredBobs = CreateRandomStringDataSet(numberOfItems);
        for (String fredBob : verifiedFredBobs)
            assertTrue(set.contains(fredBob));

    }

    @Test
    public void clear_WhenClearingSetThatContainsItems_ThenSetDoesNotContainItems() {
        // Arrange
        AVLSet set = createSetFromSequence("asdf", "qwer");

        // Act
        set.clear();

        // Assert
        boolean containsEither = containsAny(set, "asdf", "qwer");
        assertFalse(containsEither);

        boolean isEmpty = set.isEmpty();
        assertTrue(isEmpty);
    }

    private boolean containsAny(AVLSet set, String... values) {
        boolean containsChildren = false;

        for(String value : values)
            containsChildren |= set.contains(value);

        return containsChildren;
    }

    @Test
    public void clear_WhenClearingAnEmptySet_ThenSetIsEmpty() {
        // Arrange
        AVLSet set = new AVLSet();
        set.clear();

        // Act
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void isEmpty_GivenNewSet_ThenSetIsEmpty() {
        // Arrange
        AVLSet set = new AVLSet();


        // Act
        set.clear();
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void isEmpty_WhenSetContainsAnItem_ThenSetIsNotEmpty() {
        // Arrange
        AVLSet set = new AVLSet();
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
        AVLSet set = new AVLSet();
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
        AVLSet set = new AVLSet();
        String[] fredBobs = CreateRandomStringDataSet(100);
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
        AVLSet set = new AVLSet();
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
        AVLSet set = createSetFromSequence("one", "two", "three");
        set.remove("two");

        // Act
        int size = set.size();

        // Assert
        assertEquals(2, size);
    }

    @Test
    public void remove_WhenRemovingLeaf_ThenSetDoesNotContainLeaf() {
        // Arrange
        AVLSet set = createSetFromSequence("B", "A");

        // Act
        set.remove("A");

        // Assert
        boolean contains = set.contains("A");
        assertFalse(contains);
    }

    @Test
    public void remove_RemovingAnItemThatDoesNotExist_ReturnsFalse() {
        // Arrange
        AVLSet set = createSetFromSequence("A", "B");

        // Act
        set.remove("C");

        // Assert
        boolean containsBoth = setContainsAll(set, "A", "B");
        assertTrue(containsBoth);
    }

    @Test
    public void remove_WhenRemovingItemWithOnlyLeftChild_ThenItemIsRemovedAndSetContainsChild() {
        // Arrange
        AVLSet set = createSetFromSequence("H", "P", "L", "K", "M");

        // Act
        set.remove("P");

        // Assert
        boolean containsChildren = setContainsAll(set, "L", "K", "M");
        assertTrue(containsChildren);

        boolean containsRemovedItem = set.contains("P");
        assertFalse(containsRemovedItem);
    }

    @Test
    public void remove_WhenRemovingItemWithOnlyRightChild_ThenItemIsRemovedAndSetContainsChild() {
        // Arrange
        AVLSet set = createSetFromSequence("Z", "P", "T", "Q", "V");

        // Act
        set.remove("P");

        // Assert
        boolean containsRemovedItem = set.contains("P");
        assertFalse(containsRemovedItem);

        boolean containsChildren = setContainsAll(set, "T", "Q", "V");
        assertTrue(containsChildren);
    }

    @Test
    public void remove_WhenRemovingItemWithTwoChildren_ThenItemIsRemovedAndSetContainsChildren() {
        // Arrange
        AVLSet set = createSetFromSequence("H", "P", "T", "V", "U", "M", "K", "N");

        // Act
        set.remove("P");

        // Assert
        boolean containsRemovedItem = set.contains("P");
        assertFalse(containsRemovedItem);

        boolean containsChildren = setContainsAll(set, "T", "V", "U", "M", "K", "N", "H");
        assertTrue(containsChildren);

    }

    private boolean setContainsAll(AVLSet set, String... values) {
        boolean containsChildren = true;

        for(String value : values)
            containsChildren &= set.contains(value);

        return containsChildren;
    }

    @Test
    public void remove_WhenRemovingItemThatCausesCascadingRemove_ThenOnlyOneItemIsRemoved() {
        // Arrange
        AVLSet set = createSetFromSequence("M", "Y", "Z", "P", "O", "U", "S", "R");

        // Act
        set.remove("Y");

        // Assert
        boolean containsRemovedItem = set.contains("Y");
        boolean containsChildren = setContainsAll(set, "M", "Z", "P", "O", "U", "S","R");

        assertTrue(containsChildren);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void remove_WhenRemovingRootNodeWithTwoChildren_ThenOnlyRootNodeIsRemoved() {
        // Arrange
        AVLSet set = createSetFromSequence("M", "L", "Z", "P", "A");

        // Act
        set.remove("M");

        // Assert
        boolean containsChildren = set.contains("L");
        assertTrue(containsChildren);

        boolean containsRemovedItem = setContainsAll(set, "M", "Z", "P", "A");
        assertFalse(containsRemovedItem);
    }

    @Test
    public void remove_WhenRemovingRootNodeWithOneChild_ThenOnlyRootNodeIsRemoved() {
        // Arrange
        AVLSet set = createSetFromSequence("A", "B", "C");

        // Act
        set.remove("A");

        // Assert
        boolean containsRemovedItem = set.contains("A");
        assertFalse(containsRemovedItem);

        boolean containsChildren = setContainsAll(set, "B", "C");
        assertTrue(containsChildren);
    }

    @Test
    public void remove_WhenRemovingRandomItemFromHugeSet_ThenOnlyRandomItemIsRemoved() {
        // Arrange
        int numberOfItems = 100;
        AVLSet set = new AVLSet();
        String[] addedFredBobs = CreateRandomStringDataSet(numberOfItems);

        for (String fredBob : addedFredBobs)
            set.add(fredBob);

        // Act
        String[] verifyingFredBobs = CreateRandomStringDataSet(numberOfItems);
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
        AVLSet set = createSetFromSequence("M", "Y", "A", "Z", "P", "O", "U", "S", "R");

        // Act
        String inOrderResult = set.toStringInOrder();

        // Assert
        assertEquals("[A, M, O, P, R, S, U, Y, Z]", inOrderResult);

    }

    @Test
    public void toStringInOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        AVLSet set = new AVLSet();

        // Act
        String emptyResult = set.toStringInOrder();

        // Assert
        assertEquals("[]", emptyResult);

    }

    @Test
    public void toStringPreOrder_WhenGettingPreOrderString_ThenItemsAreInPreOrder() {
        // Arrange
        AVLSet set = createSetFromSequence("M", "Y", "A", "Z", "P", "O", "U", "S", "R");

        // Act
        String preOrderResult = set.toStringPreOrder();

        // Assert
        assertEquals("[M, A, Y, P, O, U, S, R, Z]", preOrderResult);

    }

    @Test
    public void toStringPreOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        AVLSet set = new AVLSet();

        // Act
        String emptyResult = set.toStringPreOrder();

        // Assert
        assertEquals("[]", emptyResult);

    }

    @Test
    public void toStringPostOrder_WhenGettingPostOrderString_ThenItemsAreInPostOrder() {
        // Arrange
        AVLSet set = createSetFromSequence("MMM", "YYY", "AAA", "ZZZ", "PPP", "OOO", "UUU", "SSS", "RRR");

        // Act
        String postOrderResult = set.toStringPostOrder();

        // Assert
        assertEquals("[AAA, OOO, RRR, SSS, UUU, PPP, ZZZ, YYY, MMM]", postOrderResult);

    }

    @Test
    public void toStringPostOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        AVLSet set = new AVLSet();

        // Act
        String emptyResult = set.toStringPostOrder();

        // Assert
        assertEquals("[]", emptyResult);

    }

    @Test
    public void remove_WhenRemovingItem_ThenRemoveDoesNotDependOnReferenceEquality() {
        // Arrange
        AVLSet set = createSetFromSequence("AAA", "BBB");

        // Act
        set.remove("A" + "A" + "A");

        // Assert
        boolean containsRemovedItem = set.contains("AAA");
        boolean containsChild = set.contains("BBB");
        assertTrue(containsChild);
        assertFalse(containsRemovedItem);
    }

    @Test
    public void rebalance_WhenAddingValueToTheLeftLeftChild_ThenOverWeightNodeIsRotatedRight() {
        // Arrange
        AVLSet set = createSetFromSequence("G", "E", "H", "F", "I", "C", "D", "B");

        // Act
        set.add("A");

        // Assert
        assertEquals("(G (C (B A _) (E D F)) (H _ I))", set.toString());
    }

    @Test
    public void rebalance_WhenAddingValueToTheLeftRightChild_ThenOverWeightNodeIsRotateRightLeft() {
        // Arrange
        AVLSet set = createSetFromSequence("J","G","L","B","H","K","M","A","D","I","N","0","C","E");

        // Act
        set.add("F");

        // Assert
        assertEquals("(J (D (B (A 0 _) C) (G (E _ F) (H _ I))) (L K (M _ N)))", set.toString());
    }

    private AVLSet createSetFromSequence(String... args) {
        AVLSet set = new AVLSet();

        for(String value : args) {
            set.add(value);
            System.out.print("Added " + value + ": ");
            System.out.println(set.toString());
        }
        return set;
    }

    @Test
    public void rebalance_WhenAddingValueToTheRightRightChild_ThenOverWeightNodeIsRotatedLeft() {
        // Arrange
        AVLSet set = createSetFromSequence("C", "B", "E", "A", "D", "G", "F", "H");

        // Act
        set.add("I");

        // Assert
        assertEquals("(C (B A _) (G (E D F) (H _ I)))", set.toString());
    }


    
    @Test
    public void TreeAsString() {
        //Arrange
        AVLSet set = createSetFromSequence("M", "T", "O", "Z", "D", "E", "A", "B");

        // Act
        String treeRepresentation = set.toString();

        // Assert
        assertEquals("(O (E (D A B) M) (T _ Z))", treeRepresentation);
        System.out.println(treeRepresentation);
    }

    @Test
    public void hugeTree() {
        //Arrange
        AVLSet set = new AVLSet();
        String[] values =  CreateRandomStringDataSet(4);
        for(String value : values)
                set.add(value);

        // Act
        String treeRepresentation = set.toString();

        // Assert
        System.out.println(treeRepresentation);
    }

    private String[] CreateRandomStringDataSet(int numberOfElements) {
        String[] testData = new String[numberOfElements];
        for (int i = 1; i <= testData.length; i++) {
            testData[i - 1] = String.format("%02d", i);
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
}
