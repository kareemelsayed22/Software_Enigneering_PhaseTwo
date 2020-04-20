import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

// This class store details of a URL that need to be printed to
// output file for a specific input URL.
//
// Reference: GetURLInfo.java from "Java in a Nutshell" by David Flanagan. 
public class URLInfo {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6";
	 
	private String urlExternalForm;
	private String contentType;
	private long contentLength;
	private long expiration;
	private Date lastModified;
	private String encoding;
	private URL url;
	
	// Class constructor that takes a URL string and gets all information
	// from the URL.
	// Throws Exception if its an invalid URL
	public URLInfo(String link) throws Exception {
		System.out.println(link);
		URL url = new URL(link);
		URLConnection urlConn = url.openConnection();
		urlConn.setRequestProperty("AGENT", USER_AGENT);
		urlExternalForm = urlConn.getURL().toExternalForm();
		contentType = urlConn.getContentType();
		contentLength = urlConn.getContentLengthLong();
		lastModified = new Date(urlConn.getLastModified());
		expiration = urlConn.getExpiration();
		encoding = urlConn.getContentEncoding();
		this.url = urlConn.getURL();
	}

	// Get content type
	public String getContentType() {
		return contentType;
	}

	// Get content length
	public long getContentLength() {
		return contentLength;
	}

	// Get last modified date
	public Date getLastModified() {
		return lastModified;
	}

	// Get external form string representation
	public String getUrlExternalForm() {
		return urlExternalForm;
	}

	// Get the expiration
	public long getExpiration() {
		return expiration;
	}

	// Get the content encoding type
	public String getEncoding() {
		return encoding;
	}
			
	// Get the URL
	public URL getURL() {
		return url;
	}
}
