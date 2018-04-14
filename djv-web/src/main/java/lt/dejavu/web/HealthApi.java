package lt.dejavu.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.health}")
public class HealthApi {
    @GetMapping(value = "/")
    public boolean healthCheck() {
        return true;
    }
}
