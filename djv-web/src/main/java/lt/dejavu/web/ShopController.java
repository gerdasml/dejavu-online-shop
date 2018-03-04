package lt.dejavu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopController {
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }
}
