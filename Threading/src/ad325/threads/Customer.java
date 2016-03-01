package ad325.threads;

/**
 * A helper class to represent the customer
 */
public class Customer {
    // the character displayed by the customer
    final char id;
    /**
     * Create a Customer with a character designation
     * @param c The customer's character designation
     */
    public Customer(char c) {
        id = c;
    }
    /**
     * The string representation of the Customer is 
     * simply the character.
     * @return The string representation
     */
    @Override
    public String toString() {
        return "" + id;
    }
}