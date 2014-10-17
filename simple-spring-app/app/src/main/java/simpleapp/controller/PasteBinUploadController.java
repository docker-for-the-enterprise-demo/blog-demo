package simpleapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/pastebin")
public class PasteBinUploadController {
	private static final Logger logger = LoggerFactory
			.getLogger(PasteBinUploadController.class);

	@RequestMapping(value = "/upload", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public String sendToPasteBin(@RequestParam(required = false) String content) {
		logger.trace("Uploading content:\n" + content + "\n");
		return "Success";
	}
}
