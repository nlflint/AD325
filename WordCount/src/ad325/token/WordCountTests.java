package ad325.token;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nate on 3/8/16.
 */
public class WordCountTests {
    private static final boolean sortByCount = true;
    private static final boolean sortAlphabetically = false;
    @Test
    public void tokenTypeCount_WhenTwoOfTheSameTokens_ThenMapHasOneEntryWithCountOfTwo() {
        // Arrange
        String twoSametoken = " Two Two ";
        InputStream inputStream = new ByteArrayInputStream(twoSametoken.getBytes());

        // Act
        WordCount wordCount = getWordCountAlphabeticallySorted();
        Map<String,Counter> countsByType = wordCount.getCountByTokenType(inputStream);

        // Assert
        assertEquals(1, countsByType.size());
        assertEquals(2, countsByType.get("two").GetCount());

    }

    private WordCount getWordCountAlphabeticallySorted() {
        return new WordCount(sortAlphabetically);
    }

    @Test
    public void tokenTypeCount_WhenCasingIsDifferent_ThenCountIsNotCaseSensitive() {
        // Arrange
        String twoSametoken = " TWO two TwO ";
        InputStream inputStream = new ByteArrayInputStream(twoSametoken.getBytes());

        // Act
        WordCount wordCount = getWordCountAlphabeticallySorted();
        Map<String,Counter> countsByType = wordCount.getCountByTokenType(inputStream);

        // Assert
        assertEquals(1, countsByType.size());
        assertEquals(3, countsByType.get("two").GetCount());

    }

    @Test
    public void tokenTypeCount_GivenMultipleTypes_ThenCountExistsForEachType() {
        // Arrange
        String twoSametoken = " TWO lmnop asd ASD two TwO Asd LMNoP ";
        InputStream inputStream = new ByteArrayInputStream(twoSametoken.getBytes());

        // Act
        WordCount wordCount = getWordCountAlphabeticallySorted();
        Map<String,Counter> countsByType = wordCount.getCountByTokenType(inputStream);

        // Assert
        assertEquals(3, countsByType.size());
        assertEquals(3, countsByType.get("two").GetCount());
        assertEquals(3, countsByType.get("asd").GetCount());
        assertEquals(2, countsByType.get("lmnop").GetCount());

    }

    @Test
    public void tokenTypeCount_GivenPunctuation_ThenPunctuationHasCount() {
        // Arrange
        String twoSametoken = " ! . ? ? ";
        InputStream inputStream = new ByteArrayInputStream(twoSametoken.getBytes());

        // Act
        WordCount wordCount = getWordCountAlphabeticallySorted();
        Map<String,Counter> countsByType = wordCount.getCountByTokenType(inputStream);

        // Assert
        assertEquals(3, countsByType.size());
        assertEquals(1, countsByType.get("!").GetCount());
        assertEquals(1, countsByType.get(".").GetCount());
        assertEquals(2, countsByType.get("?").GetCount());
    }

    @Test
    public void tokenTypeCount_GivenDoubleSpace_ThenDoubleSpaceDoesNotHaveCount() {
        // Arrange
        String twoSametoken = " house  house ";
        InputStream inputStream = new ByteArrayInputStream(twoSametoken.getBytes());

        // Act
        Map<String,Counter> countsByType = getWordCountAlphabeticallySorted().getCountByTokenType(inputStream);

        // Assert
        assertEquals(1, countsByType.size());
        assertEquals(2, countsByType.get("house").GetCount());
    }

    @Test
    public void tokenTypeCount_GivenWhiteSpaceCharacters_ThenWhiteSpaceCharactersAreNotCounted() {
        // Arrange
        String twoSametoken = "\n house\t  house cat\t cat\n \nmouse ";
        InputStream inputStream = new ByteArrayInputStream(twoSametoken.getBytes());

        // Act
        Map<String,Counter> countsByType = getWordCountAlphabeticallySorted().getCountByTokenType(inputStream);

        // Assert
        assertEquals(3, countsByType.size());
        assertEquals(2, countsByType.get("house").GetCount());
        assertEquals(2, countsByType.get("cat").GetCount());
        assertEquals(1, countsByType.get("mouse").GetCount());
    }

    @Test
    public void buildReport_GivenFilenameAndOneTokenWith20Occurances_ThenReportShowsFilenameAndTokenCount() {
        // Arrange
        String filename = "myfile.txt";
        HashMap<String, Counter> countsByToken = new HashMap<String, Counter>();
        countsByToken.put("first", new FakeCounter(20));

        // Act
        List<String> report = getWordCountAlphabeticallySorted().buildReport(filename, countsByToken);

        // Assert
        assertEquals(Arrays.asList(
                "myfile.txt: 1 tokens",
                "   20 : first",
                ""), report);
    }

    @Test
    public void buildReport_GivenMultipleTokenTypes_ThenTokensSortAlphabetically() {
        // Arrange
        String filename = "myfile.txt";
        HashMap<String, Counter> countsByToken = new HashMap<String, Counter>();
        countsByToken.put("aaa", new FakeCounter(20));
        countsByToken.put("ccc", new FakeCounter(3));
        countsByToken.put("bbb", new FakeCounter(200));

        // Act
        List<String> report = getWordCountAlphabeticallySorted().buildReport(filename, countsByToken);

        // Assert
        assertEquals(Arrays.asList(
                "myfile.txt: 3 tokens",
                "   20 : aaa",
                "  200 : bbb",
                "    3 : ccc",
                ""), report);
    }

    @Test
    public void buildReport_GivenCountsLessThanThree_ThenTokensLessThanThreeAreNotReported() {
        // Arrange
        String filename = "myfile.txt";
        HashMap<String, Counter> countsByToken = new HashMap<String, Counter>();
        countsByToken.put("aaa", new FakeCounter(1));
        countsByToken.put("ccc", new FakeCounter(2));
        countsByToken.put("zzz", new FakeCounter(3));
        countsByToken.put("bbb", new FakeCounter(500));
        countsByToken.put("fff", new FakeCounter(10));


        // Act
        List<String> report = getWordCountAlphabeticallySorted().buildReport(filename, countsByToken);

        // Assert
        assertEquals(Arrays.asList(
                "myfile.txt: 3 tokens",
                "  500 : bbb",
                "   10 : fff",
                "    3 : zzz",
                ""), report);
    }

    @Test
    public void buildReport_GivenContructorSortsByCount_ThenTokensAreSortByCountDescending() {
        // Arrange
        String filename = "myfile.txt";
        HashMap<String, Counter> countsByToken = new HashMap<String, Counter>();
        countsByToken.put("zzz", new FakeCounter(3));
        countsByToken.put("bbb", new FakeCounter(45));
        countsByToken.put("fff", new FakeCounter(10));


        // Act
        List<String> report = getWordCountAlphabeticallySorted().buildReport(filename, countsByToken);

        // Assert
        assertEquals(Arrays.asList(
                "myfile.txt: 3 tokens",
                "   45 : bbb",
                "   10 : fff",
                "    3 : zzz",
                ""), report);
    }

    private class FakeCounter extends Counter {
        private final int _count;
        public FakeCounter(int count) {
            _count = count;
        }

        @Override
        public int GetCount() {
            return _count;
        }
    }
}
