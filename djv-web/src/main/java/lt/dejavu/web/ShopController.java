package lt.dejavu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {
    @GetMapping(value = "/")
    public String index() {
        return "index.html";
    }
}
