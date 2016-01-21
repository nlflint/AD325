/**
 * Created by nate on 1/20/16.
 */
public class PostOrderIteratorTests extends StringIteratorTests {
    @Override
    public String getExpectedOrder() {
        return "A O R S U P Z Y M ";
    }

    @Override
    public StringIterator createIterator(BSTSet set) {
        return set.iteratorPostOrder();
    }
}
