package simpleapp.controller;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import simpleapp.api.vo.DownSampleRequestVO;
import simpleapp.api.vo.DownSampleResponseVO;
import simpleapp.cache.CacheService;
import simpleapp.task.download.DownloadImageTask;
import simpleapp.task.downsample.DownSampleTask;

@Controller
@RequestMapping("/downsample")
@PropertySource("classpath:application.properties")
public class DownSampleController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${staticResourcesDir}")
	protected String staticDir;

	@Autowired
	protected CacheService cacheService;

	@Autowired
	protected DownloadImageTask dlTask;

	@Autowired
	protected DownSampleTask dsTask;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public DownSampleResponseVO postImgURL(
			@RequestBody(required = true) DownSampleRequestVO postMsg,
			HttpServletRequest request) {
		String imgUrl = postMsg.getUrl();
		logger.info("Received URL: " + imgUrl);
		String quotedRequestURI = Matcher.quoteReplacement(request.getRequestURI());
		String requestURL = request.getRequestURL().toString();
		String requestHost = requestURL.replaceFirst(quotedRequestURI + "$", "");

		DownSampleResponseVO response = cacheService.get(imgUrl);

		if (response == null) {
			response = new DownSampleResponseVO("Here are your images");
			try {
				File f = dlTask.downloadImage(imgUrl);
				Map<String, String> samples = dsTask.createDownSamples(f);
				for (Map.Entry<String, String> entry : samples.entrySet()) {
					response.getUrls().put(
							entry.getKey(),
							entry.getValue().replaceFirst(
									"^" + Matcher.quoteReplacement(staticDir),
									requestHost + "/ds"));
				}
			} catch (Exception ex) {
				logger.warn("BOOM baby!", ex);
			}

			cacheService.cache(imgUrl, response);
		} else {
			logger.info("Returning cached response");
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public DownSampleRequestVO getExampleRequestVO() {
		return new DownSampleRequestVO();
	}

}
