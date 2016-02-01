import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nate on 1/30/16.
 */
public class PolishNotationTests {
    @Test
    public void pn2rpn_GivenSimplePolishNotation_ConvertsToReversePolishNotation() {
        // Arrange
        Lukasiewicz converter = new PolishNotation();

        // Act
        String reverse = converter.pn2rpn("+ 5 2");

        // Assert
        assertEquals("5 2 +", reverse);

    }

    @Test
    public void pn2rpn_GivenNestedNotation_ConvertsToReversePolishNotation() {
        // Arrange
        Lukasiewicz converter = new PolishNotation();

        // Act
        //(5 - 3) + 2
        String reverse = converter.pn2rpn("% - 5 3 2");

        // Assert
        assertEquals("5 3 - 2 %", reverse);

    }

    @Test
    public void pn2rpn_GivenPolishExamplesFromAssignment_ThenReverseNotationFromExamplesIsGenerated() {
        // Arrange
        String[][] examples = new String[][] {
                {"+ a * b c", "a b c * +"},
                {"* + a b c", "a b + c *"},
                {"+ * a b * c d","a b * c d * +"},
                {"- - a b c", "a b - c -"},
                {"* a - b c", "a b c - *"}};
        Lukasiewicz converter = new PolishNotation();

        // Act & Assert
        int inputIndex = 0;
        int expectedOutputIndex = 1;

        for (String[] example : examples) {
            String actualOutput = converter.pn2rpn(example[inputIndex]);
            assertEquals(example[expectedOutputIndex], actualOutput);
        }
    }

    @Test
    public void rpn2pn_GivenSimpleReversePolishNotation_ConvertsToPolishNotation() {
        // Arrange
        Lukasiewicz converter = new PolishNotation();

        // Act
        String polish = converter.rpn2pn("2 5 +");

        // Assert
        assertEquals("+ 2 5", polish);

    }

    @Test
    public void rpn2pn_GivenNestedReverseNotation_ConvertsToPolishNotation() {
        // Arrange
        Lukasiewicz converter = new PolishNotation();

        // Act
        //(5 - 3) + 2
        String reverse = converter.rpn2pn("5 3 % 2 +");

        // Assert
        assertEquals("+ % 5 3 2", reverse);

    }

    @Test
    public void rpn2pn_GivenReversePolishExamplesFromAssignment_ThenPolishNotationFromExamplesIsGenerated() {
        // Arrange
        String[][] examples = new String[][] {
                {"+ a * b c", "a b c * +"},
                {"* + a b c", "a b + c *"},
                {"+ * a b * c d","a b * c d * +"},
                {"- - a b c", "a b - c -"},
                {"* a - b c", "a b c - *"}};
        Lukasiewicz converter = new PolishNotation();

        // Act & Assert
        int inputIndex = 1;
        int expectedOutputIndex = 0;

        for (String[] example : examples) {
            String actualOutput = converter.rpn2pn(example[inputIndex]);
            assertEquals(example[expectedOutputIndex], actualOutput);
        }
    }
}
