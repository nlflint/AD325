import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by nate on 1/9/16.
 */
public class ArrayStackTests extends StackTests{
    public NumberStack CreateStack() {
        return new ArrayStack();
    }
}
