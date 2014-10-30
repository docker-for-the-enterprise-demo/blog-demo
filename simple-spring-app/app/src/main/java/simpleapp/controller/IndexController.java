package simpleapp.controller;


import org.springframework.web.bind.annotation.RequestMapping;

public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "Nice! You've hit the index controller. Now try /pastebin/upload?content=helloworld";
    }
}
