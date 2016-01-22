/**
 * This is the improved version of the StringSet family of interfaces. The 
 * enhancement changes the return value of the family toString methods. They
 * now include punctuation to make the structure more aesthetically pleasing
 * and clearer.
 */
public interface StringSet_Improved extends StringSet_Minus {
    
    /**
     * Returns a String containing all of the elements in this set ordered 
     * using an in-order traversal of the underlying tree, with punctuation. 
     * The return value is enclosed in square brackets with values separated 
     * by the sequence comma-space. 
     * <p>Here are some example: <ul>
     * <li>The set containing "A" "B" "C" would return: <br>
     * <tt>[A, B, C]</tt></li>
     * <li>The set containing "Hi" would return: <br>
     * <tt>[Hi]</tt></li>
     * <li>The empty set would return: <br>
     * <tt>[]</tt></li></ul>
     * 
     * @return A string representation of the elements of the set ordered 
     * by an in-order traversal of the underlying tree, with punctuation.
     */
    public String toStringInOrder();
    
    /**
     * Returns a String containing all of the elements in this set ordered 
     * using a pre-order traversal of the underlying tree, with punctuation. 
     * The return value is enclosed in square brackets with values separated 
     * by the sequence comma-space. 
     * <p>Here are some example: <ul>
     * <li>The set containing "A" "B" "C" would return: <br>
     * <tt>[B, A, C]</tt></li>
     * <li>The set containing "Hi" would return: <br>
     * <tt>[Hi]</tt></li>
     * <li>The empty set would return: <br>
     * <tt>[]</tt></li></ul>
     * 
     * @return A string representation of the elements of the set ordered 
     * by a pre-order traversal of the underlying tree, with punctuation.
     */
    public String toStringPreOrder();
    
    /**
     * Returns a String containing all of the elements in this set ordered 
     * using a post-order traversal of the underlying tree, with punctuation. 
     * The return value is enclosed in square brackets with values separated 
     * by the sequence comma-space. 
     * <p>Here are some example: <ul>
     * <li>The set containing "A" "B" "C" would return: <br>
     * <tt>[A, C, B]</tt></li>
     * <li>The set containing "Hi" would return: <br>
     * <tt>[Hi]</tt></li>
     * <li>The empty set would return: <br>
     * <tt>[]</tt></li></ul>
     * 
     * @return A string representation of the elements of the set ordered 
     * by a post-order traversal of the underlying tree, with punctuation.
     */
    public String toStringPostOrder();
    
}
