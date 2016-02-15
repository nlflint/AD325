package ad325.data_structres.tests;

import ad325.data_structures.PrioritizedWords;
import org.junit.Ignore;
import org.junit.Test;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by nate on 2/14/16.
 */
@Ignore("Performance tests: should only be run manually. Takes awhile to finish.")
public class PrioritizedWordsPerformaceTests {
    @Test
    public void addAndRemove() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();
        int testScale = 30000000;
        int[] values = new int[testScale];
        for(int i = 1; i < testScale; i++)
            values[i] = i;
        shuffleArray(values);

        // act
        System.out.println("Starting Test");
        for(int i = 1; i < testScale; i++)
            queue.add("a", i);

        // assert
        int lastPriority = Integer.MIN_VALUE;
        while(queue.size() > 0) {
            String value = queue.remove();
        }
    }

    @Test
    public void addOnly() {
        // arrange
        PrioritizedWords queue = new PrioritizedWords();
        int testScale = 30000000;
        int[] values = new int[testScale];
        for(int i = 1; i < testScale; i++)
            values[i] = i;
        shuffleArray(values);

        // act
        System.out.println("Starting Test");
        for(int i = 1; i < testScale; i++)
            queue.add("a", i);
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
