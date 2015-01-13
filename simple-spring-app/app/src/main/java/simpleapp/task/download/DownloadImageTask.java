package simpleapp.task.download;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import simpleapp.util.DownSampleUtil;

@Configuration
@Component
@PropertySource("classpath:application.properties")
public class DownloadImageTask {

	private static final Pattern IMG_FORMAT_PATTERN = Pattern.compile("image/(\\w+);|$");

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${downloadDir}")
	protected String downloadDir;

	public File downloadImage(String url) throws MalformedURLException, IOException, NoSuchAlgorithmException {
		logger.info("Checking content type of " + url);
		URL imgUrl = new URL(url);

		String imgFormat = getImageFormat(url);
		logger.info("Image format of " + url + ": " + imgFormat);
		logger.info("Reading image @ " + url);
		BufferedImage img = ImageIO.read(imgUrl);
		String md5Hash = DownSampleUtil.genMD5HashForImage(img, imgFormat);
		logger.info("MD5 of image: " + md5Hash);
		String fileName = md5Hash + "." + imgFormat;
		File f = new File(downloadDir + File.separator + fileName);
		ImageIO.write(img, imgFormat, f);
		logger.info("Wrote " + url + " to " + f.getAbsolutePath());
		return f;
	}

	private static String getImageFormat(String url) {
		String imgFormat = null;
		try {
			URL imgUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect();
			String contentType = conn.getContentType();
			conn.disconnect();

			Matcher m = IMG_FORMAT_PATTERN.matcher(contentType);
			if (m.matches()) {
				imgFormat = m.group(1);
			}
			if (imgFormat == null) {
				imgFormat = FilenameUtils.getExtension(url);
			}
		} catch (Exception ex) {
			// do nothing... REGEX just failed, no real problem.
		}
		if (imgFormat == null) {
			imgFormat = "png";
		}

		return imgFormat;
	}
}
