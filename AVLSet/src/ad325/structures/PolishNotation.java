package ad325.structures;
import java.util.Scanner;
/**
 * @author Nathan Flint
 * Assignment 3
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
        printReversePolish(root, stringBuilder, 0);

        return stringBuilder.toString();
    }

    private void printReversePolish(Node node, StringBuilder stringBuilder, int treeLevel) {
        if (node == null) return;
        printReversePolish(node.left, stringBuilder, treeLevel + 1);
        printReversePolish(node.right, stringBuilder, treeLevel + 1);
        stringBuilder.append(node.value);

        if (!isRoot(treeLevel))
            stringBuilder.append(" ");
        return;
    }

    private boolean isRoot(int treeLevel) {
        return treeLevel == 0;
    }

    private Node buildTreeFromPolish(Scanner scanner) {
        Node newNode = new Node(scanner.next());
        if (!isOperator(newNode.value))
            return newNode;

        newNode.left = buildTreeFromPolish(scanner);
        newNode.right = buildTreeFromPolish(scanner);
        return newNode;
    }

    private boolean isOperator(String value) {
        for(String operator : operators)
            if (value.equals(operator))
                return true;
        return false;
    }

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
        Stack<String> stack = buildStack(s);
        Node root = buildTreeFromReversePolish(stack);

        StringBuilder stringBuilder = new StringBuilder();
        printPolish(root, stringBuilder, 0);

        return stringBuilder.toString();
    }

    private void printPolish(Node node, StringBuilder stringBuilder, int treeLevel) {
        if (node == null) return;

        if (!isRoot(treeLevel))
            stringBuilder.append(" ");
        stringBuilder.append(node.value);


        printPolish(node.right, stringBuilder, treeLevel + 1);
        printPolish(node.left, stringBuilder, treeLevel + 1);

    }

    private Node buildTreeFromReversePolish(Stack<String> stack) {
        Node newNode = new Node(stack.pop());

        if (!isOperator(newNode.value))
            return newNode;

        newNode.left = buildTreeFromReversePolish(stack);
        newNode.right = buildTreeFromReversePolish(stack);
        return newNode;
    }

    private Stack<String> buildStack(String s) {
        Stack<String> stack = new Stack<>();
        Scanner scanner = buildScanner(s);

        while(scanner.hasNext())
            stack.push(scanner.next());

        return stack;
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
