package simpleapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import simpleapp.util.Bytes;

@Controller
@RequestMapping(value = "/convert")
public class ImageUploadController {
	private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    /**
     * Use the following to test:
     *
     *      curl -v --include --form image=@path.to.image.jpg host:port/convert/upload
     *
     *
     * @param file An image to be converted
     * @return The status of the upload
     */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String convert(@RequestParam("image") MultipartFile file) {
        if(!file.isEmpty()) {
            logger.trace("Receiving Image of size " + file.getSize() + " (" + Bytes.humanReadableByteCount(file.getSize()) + ")");
            return "Success";
        } else {
            logger.trace("File is empty..");
        }
        return "Failure";
	}

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody String info() {
        return "Use POST instead to upload an image";
    }
}
