package simpleapp.task.downsample;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import simpleapp.util.DownSampleUtil;

import com.google.common.collect.Maps;

@Configuration
@PropertySource("classpath:application.properties")
public class DownSampleTask {

	private static final String CONFIG_PFX = "sample.config.";
	private static final String SIZE_SFX = ".size";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("#{'${sample.names}'.split(',')}")
	protected List<String> configNames;

	@Value("${staticResourcesDir}")
	protected String staticDir;

	@Autowired
	protected Environment env;

	public Map<String, String> createDownSamples(File orig) throws Exception {
		Map<String, String> urls = Maps.newHashMap();
		for (String config : configNames) {
			String sizeProp = env.getProperty(CONFIG_PFX + config + SIZE_SFX);
			int size = Integer.valueOf(sizeProp);
			File sample = downsampleImage(orig, size);
			urls.put(config, sample.getAbsolutePath());
		}
		return urls;
	}

	public File downsampleImage(File orig, int size) throws Exception {
		// This is relatively safe since we are creating the files and we are
		// using the format as the extension
		String fmt = FilenameUtils.getExtension(orig.getAbsolutePath());
		BufferedImage img = Scalr.resize(ImageIO.read(orig), size, (BufferedImageOp[]) null);
		String filename = DownSampleUtil.genMD5HashForImage(img, fmt) + "." + fmt;
		File sample = new File(staticDir + File.separator + filename);
		logger.info("Sampled " + orig.getAbsolutePath() + " to size " + size + " and now saving to "
				+ sample.getAbsolutePath());
		ImageIO.write(img, fmt, sample);
		return sample;
	}
}
