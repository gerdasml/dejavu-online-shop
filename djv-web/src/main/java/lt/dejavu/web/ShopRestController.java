package lt.dejavu.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.basePath}")
public class ShopRestController {
    @RequestMapping(value = "/health", method= RequestMethod.GET)
    public boolean healthCheck() {
        return true;
    }
}
