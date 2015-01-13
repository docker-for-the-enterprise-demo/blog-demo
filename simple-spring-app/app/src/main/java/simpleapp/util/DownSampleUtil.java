package simpleapp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

public class DownSampleUtil {

	public static String genMD5HashForImage(BufferedImage img) throws NoSuchAlgorithmException, IOException {
		return genMD5HashForImage(img, "png");
	}

	public static String genMD5HashForImage(BufferedImage img, String formatName)
			throws IOException,
			NoSuchAlgorithmException {
		String md5Hash = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(img, formatName, baos);
			baos.flush();
			byte[] imgBytes = baos.toByteArray();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5Hash = DatatypeConverter.printHexBinary(md5.digest(imgBytes));
		}
		return md5Hash;
	}

}
