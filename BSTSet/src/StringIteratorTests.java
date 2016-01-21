import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nate on 1/20/16.
 */
public abstract class StringIteratorTests {
    public abstract String getExpectedOrder();
    public abstract StringIterator createIterator(BSTSet set);

    @Test
    public void whenTraversingIterator_ThenItemsAreInProperOrder() {
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
        StringIterator iterator = createIterator(set);

        // Act
        String result = "";
        while (iterator.hasNext())
            result += iterator.next() + " ";

        // Assert
        assertEquals(getExpectedOrder(), result);

    }

    @Test
    public void whenRemovingAnItem_ThenItemIsRemovedFromSet() {
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
        StringIterator iterator = createIterator(set);

        // Act
        while (iterator.hasNext())
            if (iterator.next() == "U")
                iterator.remove();

        // Assert
        boolean containsU = set.contains("U");
        assertFalse(containsU);
    }

    @Test
    public void callingNextWhenIteratorHasNoItems_ThrowsIllegalStateException() {
        // Arrange
        BSTSet set = new BSTSet();
        set.add("M");
        StringIterator iterator = createIterator(set);

        // Act & assert
        iterator.next();

        try {
            iterator.next();
        }
        catch (IllegalStateException ex) {
            assertEquals("There are no more items", ex.getMessage());
            return;
        }
        fail();
    }
}
