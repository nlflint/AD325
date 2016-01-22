import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by nathanf on 1/21/2016.
 */
public class AVLSetTests extends AVLSet {
    @Before
    public void BeforeEachTest() {
        clear();
    }

    @Test
    public void TreeAsString() {
        //Arrange
        add("M");
        add("T");
        add("O");
        add("Z");
        add("D");
        add("E");
        add("A");
        add("B");

        // Act
        String treeRepresentation = getStringRepresentation();

        // Assert
        assertEquals("(M (D (A () (B)) (E)) (T (O) (Z)))", treeRepresentation);
    }

    private String getStringRepresentation() {
        return getStringRepresentationRecursive(root);
    }

    private String getStringRepresentationRecursive(AVLNode currentNode) {
        if (currentNode == null)
            return "()";

        String left = getStringRepresentationRecursive(currentNode.left);
        String right = getStringRepresentationRecursive(currentNode.right);
        String optionalSpace = " ";

        if ((left == "()") && (right == "()")) {
            optionalSpace = left = right = "";
        }

        return "(" + currentNode.value + optionalSpace + left + optionalSpace + right + ")";
    }

    private String[] CreateRandomStringDataSet(int numberOfElements) {
        String[] testData = new String[numberOfElements];
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
}
