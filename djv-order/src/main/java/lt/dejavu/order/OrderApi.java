package lt.dejavu.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.order}")
public class OrderApi {
    @GetMapping("/")
    public String test() {
        return "ok";
    }

}
