import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// This class implements the functionality to read html/text
// content from a URLInfo instance
//
// Reference: WebpageReaderWithoutAgent.java
public class GetURLText {

	// Read html/text content of the URL and return entire content
	// as a List of String
	// Return null if there is any error
	public static List<String> readFromURL(URLInfo urlInfo) {
		List<String> lines = new ArrayList<String>();
		URL url = urlInfo.getURL();
		try {
			InputStreamReader isr = new InputStreamReader(url.openStream());
			BufferedReader br = new BufferedReader(isr);
			String line;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
			isr.close();
		} catch (IOException e) {
			return null;
		}
		return lines;
	}
	
	
}
