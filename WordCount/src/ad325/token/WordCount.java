package ad325.token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;

/**
 * This program takes multiple files as input on the command line
 * and outputs a small report on the word count of each file.
 *
 * @author Nathan Flint
 * Date: 3/12/2016
 * Level: Challenge
 */
public class WordCount {
    // The minumum occurance of a word for it to appear in the report.
    private static final int MINIMUM_TOKEN_COUNT = 3;
    // Indicates the tokens should be print by occurances, descending order
    private static boolean IsReportSortedByCount;
    // Variable for a function that will do the sorting.
    private final ToIntBiFunction<Map.Entry<String, Counter>,Map.Entry<String, Counter>> sortFunction;

    /**
     * Constructor for the word count class.
     *
     * @param isReportSortedByCount Specifies if the report output should be sorted by count.
     */
    public WordCount(boolean isReportSortedByCount) {
        if (isReportSortedByCount)
            sortFunction = this::byCount;
        else
            sortFunction = this::alphabetically;
    }

    /**
     * Starting point for command line program
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Config config = processArgs(args);

        if (config.HasError) {
            printUsage(config.ErrorMessage);
            return;
        }

        IsReportSortedByCount = config.IsReportSortedByCount;

        for (String file : config.Files)
            tokenCount(file);
    }

    // Prints usage and an optional error message.
    private static void printUsage(String errorMessage) {
        String error = "";

        if (errorMessage != null && !errorMessage.equals(""))
            error = String.format("Error: %s \n\n", errorMessage);

        System.out.print(error +
                "Usage: WordCount [-o] <file>...\n\n" +
                "Option:\n" +
                "-o\t\tSort token types by count, descending order\n\n" +
                "Example:\n" +
                "WordCount -o file1.txt file2.txt file3.txt");
    }

    /**
     * Takes command line arguments and parses them into a Config object.
     * @param args the command line arguments
     * @return a config object that is a structured representation of the command line args.
     */
    static Config processArgs(String[] args) {
        boolean orderByCount = false;
        String[] files;

        if (args.length > 0 && args[0].equals("-o")) {
            orderByCount = true;
            files = Arrays.copyOfRange(args, 1, args.length);
        } else {
            files = args;
        }

        boolean hasErrors = false;
        String errorMessage = "";

        if (files.length == 0) {
            hasErrors = true;
            errorMessage = "No files specified";
        }


        return new Config(orderByCount, files, hasErrors, errorMessage);
    }

    /**
     * Takes the given file name and creates prints a report to the console that
     * shows the token type counts
     * @param filename file name to open and get token counts
     */
    public static void tokenCount(String filename) {
        WordCount wordCount = new WordCount(IsReportSortedByCount);

        InputStream fileStream = getFileInputStream(filename);

        if (fileStream == null) {
            System.out.print(String.format("Unable to open file: %s \n\n ",filename));
            return;
        }

        Map<String, Counter> countsByTokenType = wordCount.getCountByTokenType(fileStream);
        List<String> report = wordCount.buildReport(filename, countsByTokenType);
        wordCount.printReport(report);
	}

    // takes a list of strings and prints them to the console.
    private void printReport(List<String> report) {
        for(String row : report)
            System.out.println(row);
    }

    // Gets an stream given from the given file name.
    private static InputStream getFileInputStream(String filename) {
        try {
            return new FileInputStream(filename);
        }
        catch (FileNotFoundException ex)
        {
            return null;
        }
    }

    // Creates a map of counts by token type.
    Map<String,Counter> getCountByTokenType(InputStream inputStream) {
		// open the file for reading using Scanner
        java.util.Scanner scan = new java.util.Scanner(inputStream);
        scan.useDelimiter(" ");

        Map<String,Counter> countsByType = new HashMap<String, Counter>();

		while(scan.hasNext()) {
            String token = scan.next().toLowerCase().trim();
            if (token.length() > 0)
                incrementCount(countsByType, token);
		}

		scan.close();

        return countsByType;
	}

    // Increments given the token count on the given map. Adds token if it doesn't exist in the given map.
    private void incrementCount(Map<String, Counter> countsByType, String token) {
        if (countsByType.containsKey(token))
            countsByType.get(token).Increment();
        else
            countsByType.put(token, new Counter());
    }

    // Creates a report in the form of a list of strings from the given map of counts by token.
    List<String> buildReport(String filename, Map<String,Counter> countByTokenType) {
        List<Map.Entry<String, Counter>> keyValuesSorted = sortAndFilterKeyValues(countByTokenType);

        int numberOfTokens = keyValuesSorted.size();
        String header = CreateHeader(filename, numberOfTokens);

        ArrayList<String> report = new ArrayList<>();
        report.add(header);

        for (Map.Entry<String, Counter> keyValue : keyValuesSorted) {
            String tokenType = keyValue.getKey();
            int count = keyValue.getValue().GetCount();

            String row = FormatTokenCountRow(count, tokenType);
            report.add(row);
        }

        report.add("");

        return report;
    }

    // sorts and filters the given map into a list of key-value pairs.
    private List<Map.Entry<String, Counter>> sortAndFilterKeyValues(Map<String, Counter> countByTokenType) {
        return countByTokenType.entrySet()
                .stream()
                .filter(this::hasMinimumCount)
                .sorted(sortFunction::applyAsInt)
                .collect(Collectors.toList());
    }

    // A filter that determines if the given key-value pair meets the minimum count requirement
    private boolean hasMinimumCount(Map.Entry<String, Counter> token) {
        return token.getValue().GetCount() >= MINIMUM_TOKEN_COUNT;
    }

    // A sorting function that sorts alphabetically by token type
    private int alphabetically(Map.Entry<String, Counter> left, Map.Entry<String, Counter> right) {
        return left.getKey().compareTo(right.getKey());
    }

    // a sorting function that sorts the given key-value pairs by token count.
    private int byCount(Map.Entry<String, Counter> left, Map.Entry<String, Counter> right) {
        return right.getValue().GetCount() - left.getValue().GetCount();
    }

    // translates the given count and token into a formated string with correct spacing for the report.
    private String FormatTokenCountRow(int count, String tokenType) {
        return String.format("%5d : %s", count, tokenType);
    }

    // Creates a header for the report with the file name and token type counts
    private String CreateHeader(String filename, int count) {
        return String.format("%s: %d tokens", filename, count);
    }
}

/**
 * This class wraps and Integer. Useful for maps.
 */
class Counter {
    // the current count
    private int _count;

    /**
     * Constructor. Initial count is 1.
     */
    public Counter() {
        _count = 1;
    }

    /**
     * Increments this counter by 1
     */
    public void Increment() {
        _count++;
    }

    /**
     * Gets the current count.
     * @return the count
     */
    public int GetCount() {
        return _count;
    }
}

/**
 * A configuration.
 */
class Config {
    /**
     * Indicates the report shall sort the tokens by count
     */
    public final boolean IsReportSortedByCount;
    /**
     * File names to process
     */
    public final String[] Files;
    /**
     * Indicates there was an error processing the command line args.
     */
    public final boolean HasError;
    /**
     * If there was an erorr, then this contains the error message
     */
    public final String ErrorMessage;

    /**
     * Constructor
     * @param isReportSortedByCount Indicates the report shall be sorted by count
     * @param files The files that will be processed
     * @param hasError Indicates if there was an error processing the command line args.
     * @param errorMessage If there was an error, then this contains the error message.
     */
    Config(boolean isReportSortedByCount, String[] files, boolean hasError, String errorMessage) {
        IsReportSortedByCount = isReportSortedByCount;
        Files = files;
        HasError = hasError;
        ErrorMessage = errorMessage;
    }
}
