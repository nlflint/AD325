/**
 * Created by nate on 1/20/16.
 */
public class PreOrderIteratorTests extends StringIteratorTests {
    @Override
    public String getExpectedOrder() {
        return "M A Y P O U S R Z ";
    }

    @Override
    public StringIterator createIterator(BSTSet set) {
        return set.iteratorPreOrder();
    }
}
