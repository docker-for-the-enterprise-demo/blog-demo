package simpleapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("/")
    public @ResponseBody String index() {
        return "Nice! You've hit the index controller. Now upload an image to /convert/upload";
    }
}
