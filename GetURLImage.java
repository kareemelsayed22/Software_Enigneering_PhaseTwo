import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// This class implements the functionality to read an image from
// given URLInfo instance
//
// Reference: GetURLImage.java from "Java in Nutshell" by David Flanagan.
public class GetURLImage {

	// read image from URLInfo instance and return the image as BufferedImage
	// return null if there is any error
	public static BufferedImage readFromURL(URLInfo urlInfo) {
		try {
			BufferedImage image = ImageIO.read(urlInfo.getURL());
			return image;
		} catch (IOException e) {
			return null;
		}
	}
	
}
