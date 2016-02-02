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
        StringSet_Check set = new AVLSet();

        // Act
        boolean wasAdded = set.add("blah");

        // Assert
        assertTrue(wasAdded);
    }

    @Test
    public void add_WhenAddingStringASecondTime_ThenStringIsNotAdded() {
        // Arrange
        StringSet_Check set = new AVLSet();

        // Act
        set.add("blah");
        boolean wasAdded = set.add("blah");

        // Assert
        assertFalse(wasAdded);
    }

    @Test
    public void add_WhenAddingTwoDifferentString_ThenStringsAreAdded() {
        // Arrange
        StringSet_Check set = new AVLSet();

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
        StringSet_Check set = new AVLSet();

        // Act
        set.add("blah");

        // Assert
        String expectedContains = "blah";
        assertTrue(set.contains(expectedContains));
    }

    @Test
    public void contains_WhenTwoStringsAdded_ThenSetContainsBothString() {
        // Arrange
        StringSet_Check set = new AVLSet();
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
        StringSet_Check set = new AVLSet();
        set.add("blah");

        // Act
        boolean contains = set.contains("bl" + "ah");

        // Assert
        assertTrue(contains);
    }

    @Test
    public void add_AddingNullString_ThrowsException() {
        // Arrange
        StringSet_Check set = new AVLSet();

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
        StringSet_Check set = new AVLSet();

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
        int numberOfItems = 100;
        StringSet_Check set = new AVLSet();
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
        StringSet_Check set = createSetFromSequence("asdf", "qwer");

        // Act
        set.clear();

        // Assert
        boolean containsEither = containsAny(set, "asdf", "qwer");
        assertFalse(containsEither);

        boolean isEmpty = set.isEmpty();
        assertTrue(isEmpty);
    }

    private boolean containsAny(StringSet_Check set, String... values) {
        boolean containsChildren = false;

        for(String value : values)
            containsChildren |= set.contains(value);

        return containsChildren;
    }

    @Test
    public void clear_WhenClearingAnEmptySet_ThenSetIsEmpty() {
        // Arrange
        StringSet_Check set = new AVLSet();
        set.clear();

        // Act
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void isEmpty_GivenNewSet_ThenSetIsEmpty() {
        // Arrange
        StringSet_Check set = new AVLSet();


        // Act
        set.clear();
        boolean isEmpty = set.isEmpty();

        // Assert
        assertTrue(isEmpty);
    }

    @Test
    public void isEmpty_WhenSetContainsAnItem_ThenSetIsNotEmpty() {
        // Arrange
        StringSet_Check set = new AVLSet();
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
        StringSet_Check set = new AVLSet();
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
        StringSet_Check set = new AVLSet();
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
        StringSet_Check set = new AVLSet();
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
        StringSet_Check set = createSetFromSequence("one", "two", "three");
        set.remove("two");

        // Act
        int size = set.size();

        // Assert
        assertEquals(2, size);
    }

    @Test
    public void remove_WhenRemovingLeaf_ThenSetDoesNotContainLeaf() {
        // Arrange
        StringSet_Check set = createSetFromSequence("B", "A");

        // Act
        set.remove("A");

        // Assert
        boolean contains = set.contains("A");
        assertFalse(contains);
    }

    @Test
    public void remove_RemovingAnItemThatDoesNotExist_ReturnsFalse() {
        // Arrange
        StringSet_Check set = createSetFromSequence("A", "B");

        // Act
        set.remove("C");

        // Assert
        boolean containsBoth = setContainsAll(set, "A", "B");
        assertTrue(containsBoth);
    }

    @Test
    public void remove_WhenRemovingItemWithOnlyLeftChild_ThenItemIsRemovedAndSetContainsChild() {
        // Arrange
        StringSet_Check set = createSetFromSequence("H", "P", "L", "K", "M");

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
        StringSet_Check set = createSetFromSequence("Z", "P", "T", "Q", "V");

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
        StringSet_Check set = createSetFromSequence("H", "P", "T", "V", "U", "M", "K", "N");

        // Act
        set.remove("P");

        // Assert
        boolean containsRemovedItem = set.contains("P");
        assertFalse(containsRemovedItem);

        boolean containsChildren = setContainsAll(set, "T", "V", "U", "M", "K", "N", "H");
        assertTrue(containsChildren);

    }

    private boolean setContainsAll(StringSet_Check set, String... values) {
        boolean containsChildren = true;

        for(String value : values)
            containsChildren &= set.contains(value);

        return containsChildren;
    }

    @Test
    public void remove_WhenRemovingItemThatCausesCascadingRemove_ThenOnlyOneItemIsRemoved() {
        // Arrange
        StringSet_Check set = createSetFromSequence("M", "Y", "Z", "P", "O", "U", "S", "R");

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
        StringSet_Check set = createSetFromSequence("M", "L", "Z", "P", "A");

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
        StringSet_Check set = createSetFromSequence("A", "B", "C");

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
        StringSet_Check set = new AVLSet();
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
        StringSet_Check set = createSetFromSequence("M", "Y", "A", "Z", "P", "O", "U", "S", "R");

        // Act
        String inOrderResult = set.toStringInOrder();

        // Assert
        assertEquals("[A, M, O, P, R, S, U, Y, Z]", inOrderResult);

    }

    @Test
    public void toStringInOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        StringSet_Check set = new AVLSet();

        // Act
        String emptyResult = set.toStringInOrder();

        // Assert
        assertEquals("[]", emptyResult);

    }

    @Test
    public void toStringPreOrder_WhenGettingPreOrderString_ThenItemsAreInPreOrder() {
        // Arrange
        StringSet_Check set = createSetFromSequence("M", "Y", "A", "Z", "P", "O", "U", "S", "R");

        // Act
        String preOrderResult = set.toStringPreOrder();

        // Assert
        assertEquals("[P, M, A, O, Y, S, R, U, Z]", preOrderResult);

    }

    @Test
    public void toStringPreOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        StringSet_Check set = new AVLSet();

        // Act
        String emptyResult = set.toStringPreOrder();

        // Assert
        assertEquals("[]", emptyResult);

    }

    @Test
    public void toStringPostOrder_WhenGettingPostOrderString_ThenItemsAreInPostOrder() {
        // Arrange
        StringSet_Check set = createSetFromSequence("MMM", "YYY", "AAA", "ZZZ", "PPP", "OOO", "UUU", "SSS", "RRR");

        // Act
        String postOrderResult = set.toStringPostOrder();

        // Assert
        assertEquals("[AAA, OOO, MMM, RRR, UUU, SSS, ZZZ, YYY, PPP]", postOrderResult);

    }

    @Test
    public void toStringPostOrder_WhenSetIsEmpty_ThenEmptyStringReturned() {
        // Arrange
        StringSet_Check set = new AVLSet();

        // Act
        String emptyResult = set.toStringPostOrder();

        // Assert
        assertEquals("[]", emptyResult);

    }

    @Test
    public void remove_WhenRemovingItem_ThenRemoveDoesNotDependOnReferenceEquality() {
        // Arrange
        StringSet_Check set = createSetFromSequence("AAA", "BBB");

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
        assertEquals("(G (C (B A _) (E D F)) (H _ I))", toString(set.root));
    }

    @Test
    public void rebalance_WhenAddingValueToTheLeftRightChild_ThenOverWeightNodeIsRotateRightLeft() {
        // Arrange
        AVLSet set = createSetFromSequence("J","G","L","B","H","K","M","A","D","I","N","0","C","E");

        // Act
        set.add("F");

        // Assert
        assertEquals("(J (D (B (A 0 _) C) (G (E _ F) (H _ I))) (L K (M _ N)))", toString(set.root));
    }

    @Test
    public void rebalance_WhenAddingValueToTheRightLeftChild_ThenOverWeightNodeIsRotateLeftRight() {
        // Arrange
        AVLSet set = createSetFromSequence("E","C","H","B","D","G","M","A","F","K","N","J","L","O");

        // Act
        set.add("I");

        // Assert
        assertEquals("(E (C (B A _) D) (K (H (G F _) (J I _)) (M L (N _ O))))", toString(set.root));
    }

    @Test
    public void rebalance_WhenRemovingLeavesRightRightOverWeight_ThenOverWeightNodeIsRotatedLeft() {
        // Arrange
        AVLSet set = createSetFromSequence("B","A","C","D");

        // Act
        set.remove("A");

        // Assert
        assertEquals("(C B D)", toString(set.root));
    }

    @Test
    public void rebalance_WhenRemoveReplaceLeavesImbalanceAtReplacingNode_ThenLocalImbalanceIsCorrected() {
        // Arrange
        AVLSet set = createSetFromSequence("E","B","F","A","C","G","D");

        // Act
        set.remove("B");

        // Assert
        assertEquals("(E (C A D) (F _ G))", toString(set.root));
    }

    @Test
    public void rebalance_WhenRemoveLeavesLeftLeftOverWeight_ThenOverWeightNodeIsRotatedRight() {
        // Arrange
        AVLSet set = createSetFromSequence("C","B","D","A");

        // Act
        set.remove("D");

        // Assert
        assertEquals("(B A C)", toString(set.root));
    }

    @Test
    public void rebalance_WhenRemoveLeavesLeftRightOverWeight_ThenOverWeightNodeIsRotatedLeftRight() {
        // Arrange
        AVLSet set = createSetFromSequence("C","A","D","B");

        // Act
        set.remove("D");

        // Assert
        assertEquals("(B A C)", toString(set.root));
    }

    @Test
    public void rebalance_WhenRemoveLeavesRightLeftOverWeight_ThenOverWeightNodeIsRotatedRightLeft() {
        // Arrange
        AVLSet set = createSetFromSequence("B","A","D","C");

        // Act
        set.remove("A");

        // Assert
        assertEquals("(C B D)", toString(set.root));
    }

    @Test
    public void rebalance_WhenRemoveLeavesRightLeftAndRighRightChildrenEquallyWeighted_ThenOverWeightNodeIsRotatedRight() {
        // Arrange
        AVLSet set = createSetFromSequence("C","B","G","A","E","I","D","F","H","J");

        // Act
        set.remove("B");

        // Assert
        assertEquals("(G (C A (E D F)) (I H J))", toString(set.root));
    }

    @Test
    public void rebalance_WhenRemoveLeavesLeftLeftAndLeftRightChildrenEquallyWeighted_ThenOverWeightNodeIsRotatedLeft() {
        // Arrange
        AVLSet set = createSetFromSequence("H","D","I","B","F","J","A","C","E","G");

        // Act
        set.remove("I");

        // Assert
        assertEquals("(D (B A C) (H (F E G) J))", toString(set.root));
    }

    private AVLSet createSetFromSequence(String... args) {
        AVLSet set = new AVLSet();

        for(String value : args) {
            set.add(value);
            System.out.print("Added " + value + ": ");
            System.out.println(toString(set.root));
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
        assertEquals("(C (B A _) (G (E D F) (H _ I)))", toString(set.root));
    }


    
    @Test
    public void TreeAsString() {
        //Arrange
        AVLSet set = createSetFromSequence("M", "T", "O", "Z", "D", "E", "A", "B");

        // Act
        String treeRepresentation = toString(set.root);

        // Assert
        assertEquals("(O (E (B A D) M) (T _ Z))", treeRepresentation);
        System.out.println(treeRepresentation);
    }

    @Test
    public void hugeTreeIsBalancedAfterRandomlyAdding() {
        //Arrange
        AVLSet set = new AVLSet();

        // Act
        String[] values =  CreateRandomStringDataSet(1000);
        for(String value : values)
                set.add(value);

        // Assert
        assertTrue(isTreeIsBalanced(set.root));
    }

    @Test
    public void hugeTreeIsBalancedAfterRandomlyRemoving() {
        //Arrange
        AVLSet set = new AVLSet();
        String[] values =  CreateRandomStringDataSet(1000);
        for(String value : values)
            set.add(value);

        String[] valuesRemoving =  CreateRandomStringDataSet(1000);
        for(int i = 0; i < 500; i++)
            set.remove(valuesRemoving[i]);

        // Act
        for(int i = 500; i < 1000; i++)
            set.contains(valuesRemoving[i]);

        // Assert
        assertTrue(isTreeIsBalanced(set.root));
    }

    private boolean isTreeIsBalanced(Node node) {
        if (node == null)
            return true;

        boolean isLocallyBalanced = isLocallyBalanced(node);
        boolean leftOk = isTreeIsBalanced(node.left);
        boolean rightOk = isTreeIsBalanced(node.right);
        boolean allOk = leftOk && rightOk && isLocallyBalanced;

        assertTrue(allOk);

        return allOk;

    }

    private boolean isLocallyBalanced(Node node) {
        return 2 > Math.abs(
                getHeight(node.left) - getHeight(node.right));
    }

    private int getHeight(Node node) {
        if (node == null)
            return 0;

        return 1 + Math.max(
                getHeight(node.left),
                getHeight(node.right));

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

    // recursively builds a string that represents the tree's structure
    private String toString(Node node) {
        if (node == null)
            return "_";

        String left = toString(node.left);
        String right = toString(node.right);
        String optionalSpace = " ";

        if ((left == "_") && (right == "_")) {
            return node.value;
        }

        return "(" + node.value + optionalSpace + left + optionalSpace + right + ")";
    }
}
