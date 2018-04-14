package lt.dejavu.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.basePath}/health")
public class HealthApi {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public boolean healthCheck() {
        return true;
    }
}
