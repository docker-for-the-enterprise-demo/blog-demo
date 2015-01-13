package simpleapp.controller;

public class IndexController {
	public String index() {
		return "Nice! You've hit the index controller. Now try /pastebin/upload?content=helloworld";
	}
}
