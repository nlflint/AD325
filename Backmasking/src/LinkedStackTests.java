import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by nate on 1/9/16.
 */
public class LinkedStackTests extends ArrayStackTests{
    @Override
    public NumberStack CreateStack() {
        return new LinkedStack();
    }
}
