/**
 * This interface supports translating between Polish Notation (PN) and Reverse
 * Polish Notation (RPN). Since the focus of this class focuses is the 
 * manipulation of data structures, rather than parsing, we will add some 
 * requirements to simplify the processing. 
 * 
 * <p>The input string values will be space delimited, that is a space will 
 * appear between values and operators. So, the input String can be separated 
 * into tokens using java.util.Scanner. Similarly, the output string values 
 * will be space delimited. Notice that the output of one method can be used
 * as the input to the other method.
 * 
 * <p>The application shall support 5 operators: <tt>+ - * / %</tt><br>
 * The values shall begin with an alphanumeric character.
 */
public interface Lukasiewicz {

    /**
     * Converts Polish notation to reverse Polish notation.
     * @param s The Polish notation string to be converted
     * @return The reverse Polish notation representation of the same expression
     */
    public String pn2rpn(String s);
    
    /**
     * Converts reverse Polish notation to Polish notation.
     * @param s The reverse Polish notation string to be converted
     * @return The Polish notation representation of the same expression
     */
    public String rpn2pn(String s);
    
}
