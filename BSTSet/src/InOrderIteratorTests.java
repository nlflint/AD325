/**
 * Created by nate on 1/20/16.
 */
public class InOrderIteratorTests extends StringIteratorTests {
    @Override
    public String getExpectedOrder() {
        return "A M O P R S U Y Z ";
    }

    @Override
    public StringIterator createIterator(BSTSet set) {
        return set.iteratorInOrder();
    }
}
