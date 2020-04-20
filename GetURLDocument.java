import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

// This class implements the functionality to read doc/pdf
// content from a URLInfo instance
public class GetURLDocument {

	// Get the content of a pdf/doc URL as an array of bytes
	// Return null if there is any error
	// 
	// Reference: https://stackoverflow.com/questions/2295221/java-net-url-read-stream-to-byte
	public static byte[] readFromURL(URLInfo urlInfo) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try {
	        byte[] chunk = new byte[4096];
	        int bytesRead;
	        InputStream stream = urlInfo.getURL().openStream();
	        while ((bytesRead = stream.read(chunk)) > 0) {
	            outputStream.write(chunk, 0, bytesRead);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	    return outputStream.toByteArray();
	}
	
}
