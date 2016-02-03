package ad325.structures;
import java.util.Scanner;
/**
 * @author Nathan Flint
 * Assignment 3
 * Level: Plus
 *
 * This class will convert a space delimited polish notion to reverse polish notation and vice versa.
 *
 */
public class PolishNotation implements Lukasiewicz {
    String[] operators;
    public PolishNotation() {
        operators = new String[] {"+","-","*","/","%"};
    }
    /**
     * Converts Polish notation to reverse Polish notation.
     * @param s The Polish notation string to be converted
     * @return The reverse Polish notation representation of the same expression
     */
    @Override
    public String pn2rpn(String s) {
        Scanner scanner = buildScanner(s);
        Node root = buildTreeFromPolish(scanner);

        StringBuilder stringBuilder = new StringBuilder();
        buildReversePolishString(root, stringBuilder, true);

        return stringBuilder.toString();
    }

    // Builds an infix representation of the input as a binary tree
    private Node buildTreeFromPolish(Scanner scanner) {
        Node newNode = new Node(scanner.next());
        if (!isOperator(newNode.value))
            return newNode;

        newNode.left = buildTreeFromPolish(scanner);
        newNode.right = buildTreeFromPolish(scanner);
        return newNode;
    }

    // identifies if the given value is an operator
    private boolean isOperator(String value) {
        for(String operator : operators)
            if (value.equals(operator))
                return true;
        return false;
    }

    // writes reverse polish notation to the string builder.
    private void buildReversePolishString(Node node, StringBuilder stringBuilder, boolean isRoot) {
        if (node == null) return;

        buildReversePolishString(node.left, stringBuilder, false);
        buildReversePolishString(node.right, stringBuilder, false);
        stringBuilder.append(node.value);

        if (!isRoot)
            stringBuilder.append(" ");

        return;
    }

    // Builds a scanner with the correct delimiter
    private Scanner buildScanner(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter(" ");
        return scanner;
    }

    /**
     * Converts reverse Polish notation to Polish notation.
     * @param s The reverse Polish notation string to be converted
     * @return The Polish notation representation of the same expression
     */
    @Override
    public String rpn2pn(String s) {
        Scanner scanner = buildScanner(s);
        Node root = buildTreeFromReversePolish(scanner);

        StringBuilder stringBuilder = new StringBuilder();
        buildPolishString(root, stringBuilder, true);

        return stringBuilder.toString();
    }

    // Builds an infix tree from the given reverse polish string. Does it in a single pass.
    private Node buildTreeFromReversePolish(Scanner scanner) {
        Stack<Node> stack = new Stack<>();

        while (scanner.hasNext()) {
            String value = scanner.next();
            Node newNode = new Node(value);
            if (isOperator(value)) {
                newNode.right = stack.pop();
                newNode.left = stack.pop();
            }
            stack.push(newNode);
        }
        return stack.pop();
    }

    // writes the given infix tree as polish notation to the string builder
    private void buildPolishString(Node node, StringBuilder stringBuilder, boolean isRoot) {
        if (node == null) return;

        if (!isRoot)
            stringBuilder.append(" ");

        stringBuilder.append(node.value);
        buildPolishString(node.left, stringBuilder, false);
        buildPolishString(node.right, stringBuilder, false);

        return;
    }

    /**
     * This is a node used in the tree
     */
    class Node {
        // Value of the node
        String value;
        // The left and right children of this node
        Node left, right;

        /**
         * Constructor. Makes a new node with the given value and null child references.
         *
         * @param s value that this node will hold
         */
        public Node(String s) {
            value = s;
            left = right = null;
        }
    }
}
