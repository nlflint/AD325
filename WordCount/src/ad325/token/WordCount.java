package ad325.token;

import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WordCount {
 
	public static void tokenCount(String filename) {
        WordCount wordCount = new WordCount();

        InputStream fileStream = getFileInputStream(filename);

        wordCount.getCountByTokenType(fileStream);

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
            String token = scan.next().toLowerCase();
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
