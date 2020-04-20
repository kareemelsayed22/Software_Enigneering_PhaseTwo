import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

// The main driver program for the Phase 2 of the project.
public class Main {

	// Read and return the list of URL's from a file
	public static List<String> readURLFromFile(String filename){
		List<String> urls = new ArrayList<String>();
		try {
			Scanner fileReader = new Scanner(new File(filename));
			while(fileReader.hasNextLine()) {
				String url = fileReader.nextLine();
				urls.add(url);
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: input file " + filename + " not found!\n");
			System.exit(0);
		}
		return urls;
	}
	// Process all input URLs and save results to given output file and directory
	public static void processInputLinks(List<String> urls, PrintWriter out, File outDir) {
		for(String link : urls) {
			try {
				//System.out.println("Processing URL: " + link);
				URLInfo urlInfo = new URLInfo(link);
				processUrl(urlInfo, out, outDir);
				//System.out.println("OK\n");
			} catch (Exception e) {
				//System.out.println("ERROR: could not access URL: " + link);
				e.printStackTrace();
				out.println("FAILED to access URL: " + link+"\n\n");
			}			
		}
	}
	
	// Process a URL and save its details to output file and content to output directory
	public static void processUrl(URLInfo urlInfo, PrintWriter outputFile, File outDir) {
		// print the details of the URLInfo to output file
		
		outputFile.println("URL: " + urlInfo.getUrlExternalForm());
		outputFile.println("Content Type: " + urlInfo.getContentType());
		outputFile.println("Content Length: " + urlInfo.getContentLength());
		outputFile.println("Last Modified: " + urlInfo.getLastModified().toString());
		outputFile.println("Expiration: " + urlInfo.getExpiration());
		outputFile.println("Content Encoding: " + urlInfo.getEncoding());
		
		// Extract the resource name of the URL
		String url = urlInfo.getUrlExternalForm();
		int index = url.lastIndexOf('/');
		String resourceName = urlInfo.getUrlExternalForm().substring(index+1);
		
		// Get the name of file for URL to be saved in data directory
		String urlOutputFilename = outDir.getAbsolutePath()+File.separator+resourceName;
		
		String contentType = urlInfo.getContentType();
		// handle html/text URL
		if(contentType.toLowerCase().startsWith("text")) {             
			List<String> lines = GetURLText.readFromURL(urlInfo);
			saveTextLines(lines, urlOutputFilename);
			outputFile.println("URL Content Size: " + GetContentLength.getLengthBytes(urlOutputFilename) + " bytes");
			outputFile.println("Number of lines: " + lines.size());
			outputFile.println("Filename: " + resourceName);
		}
		// handle image URL
		else if(contentType.toLowerCase().startsWith("image")) {       
			BufferedImage image = GetURLImage.readFromURL(urlInfo);
			saveImageToFile(image, urlOutputFilename);
			outputFile.println("URL Content Size: " + GetContentLength.getLengthBytes(urlOutputFilename) + " bytes");
			outputFile.println("Filename: " + resourceName);
		}
		// handle doc/pdf URL
		else if(contentType.toLowerCase().startsWith("application")) { 
			byte[] bytes = GetURLDocument.readFromURL(urlInfo);
			saveBytes(bytes, urlOutputFilename);
			outputFile.println("URL Content Size: " + GetContentLength.getLengthBytes(urlOutputFilename) + " bytes");
			outputFile.println("Filename: " + resourceName);
		}
		else {
			outputFile.println("Unsupported content type!");
		}
		
		outputFile.println("\n\n");
	}
	
	// Save an image to given output file
	public static void saveImageToFile(BufferedImage image, String filename) {
		File outFile = new File(filename);
		try {
			ImageIO.write(image, "jpg", outFile);
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	// Save a list of lines to a given file
	public static void saveTextLines(List<String> lines, String filename) {
		try {
			PrintWriter pw = new PrintWriter(new File(filename));
			for(String s : lines) {
				pw.print(s);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	// Save an array of bytes to file
	public static void saveBytes(byte[] bytes, String filename) {
		try {
			OutputStream fileOut = new FileOutputStream(new File(filename));
			fileOut.write(bytes);
			fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch(IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
		
	public static void main(String[] args) {
		// Variables to store input parameters
		String outDirPath = null;
		String inputFilename = null;
		String outputFilename = null;
				
		// Check all parameters has been entered
		if(args.length != 6) {
			System.out.println("Usage: java Main FLAG ..");
			System.out.println("\nwhere      FLAG can be one of the following flags:");
			System.out.println("-d DIR       Specifies the directory of input/output files");
			System.out.println("-i FILENAME  Specifies the name of the input file of URLs");
			System.out.println("-o FILENAME  Specifies the name of the output file\n");
			System.exit(0);
		}
		// Parse the command line flags and parameters
		for(int i  = 0; i < args.length; i++) {
			String s = args[i];
			if(s.equals("-d")) {
				outDirPath = args[i+1];
			}
			else if(s.equals("-i")) {
				inputFilename = args[i+1];
			}
			else if(s.equals("-o")) {
				outputFilename = args[i+1];
			}
		}		
		// open data directory
		File dir = new File(outDirPath);
		if(!dir.exists() || !dir.isDirectory()) {
			System.out.println("ERROR: directory " + outDirPath + " not found!\n");
			System.exit(0);
		}
				
		// read the list of URLs from input file
		List<String> urls = readURLFromFile(dir.getAbsolutePath()+ File.separator+inputFilename);	
		
		// Open output file for writing
		PrintWriter out = null;
		try {
			out = new PrintWriter(new File(dir.getAbsoluteFile()+File.separator+outputFilename));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
			System.exit(0);
		}		
		
		// process all the links and save results to output file
		processInputLinks(urls, out, dir);
		
		// close the output file
		out.close();
	}

}
