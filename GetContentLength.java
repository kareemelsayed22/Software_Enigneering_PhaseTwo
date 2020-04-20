import java.io.File;

// This class implements the functionality to get the size of a file
//
// Reference: https://mkyong.com/java/how-to-get-file-size-in-java/
public class GetContentLength {

	// Get the length of a file in bytes
	public static long getLengthBytes(String filename) {
		File file = new File(filename);
		return file.length();
	}
	
	// Get the length of a file in kilo bytes
	public static long getLengthKBytes(String filename) {
		File file = new File(filename);
		return file.length() / 1024;
	}
	
	// Get the length of a file in mega bytes
	public static long getLengthMBytes(String filename) {
		File file = new File(filename);
		return (file.length() / 1024) / 1024;
	}
	
}
