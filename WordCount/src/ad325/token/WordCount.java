package ad325.token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCount {
    public static void main(String[] args) {
        tokenCount(args[0]);
    }

	public static void tokenCount(String filename) {
        WordCount wordCount = new WordCount();

        InputStream fileStream = getFileInputStream(filename);

        Map<String, Counter> countsByTokenType = wordCount.getCountByTokenType(fileStream);
        List<String> report = wordCount.buildReport(filename, countsByTokenType);
        printReport(report);
	}

    private static void printReport(List<String> report) {
        for(String row : report)
            System.out.println(row);
    }

    private static InputStream getFileInputStream(String filename) {
        try {
            return new FileInputStream(filename);
        }
        catch (FileNotFoundException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
    }

    Map<String,Counter> getCountByTokenType(InputStream inputStream) {
		// open the file for reading using Scanner
        java.util.Scanner scan = new java.util.Scanner(inputStream);
        scan.useDelimiter(" ");

        Map<String,Counter> countsByType = new HashMap<String, Counter>();

		// process the file, token by token
		while(scan.hasNext()) {
            String token = scan.next().toLowerCase().trim();
            if (token.length() > 0)
                addToken(countsByType, token);
		}

		// close the file
		scan.close();

        return countsByType;
	}

    private void addToken(Map<String, Counter> countsByType, String token) {
        if (countsByType.containsKey(token))
            countsByType.get(token).Increment();
        else
            countsByType.put(token, new Counter());
    }

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

    private List<Map.Entry<String, Counter>> sortAndFilterKeyValues(Map<String, Counter> countByTokenType) {
        return countByTokenType.entrySet()
                .stream()
                .filter(x -> x.getValue().GetCount() > 2)
                .sorted((x,y) -> x.getKey().compareTo(y.getKey()))
                .collect(Collectors.toList());
    }

    private String FormatTokenCountRow(int count, String tokenType) {
        return String.format("%5d : %s", count, tokenType);
    }

    private String CreateHeader(String filename, int count) {
        return String.format("%s: %d tokens", filename, count);
    }

}

class Counter {
    private int _count;

    public Counter() {
        _count = 1;
    }

    public void Increment() {
        _count++;
    }

    public int GetCount() {
        return _count;
    }
}
