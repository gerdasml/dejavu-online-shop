package lt.dejavu.web;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ShopController {
    @GetMapping(value = "/")
    public String index() {
        return "index.html";
    }

    @GetMapping(value = "/robots.txt")
    public void robots(HttpServletResponse response) throws IOException {
        try (InputStream resourceStream = new ClassPathResource("robots.txt").getInputStream()) {
            response.addHeader("Content-disposition", "filename=robot.txt");
            response.setContentType("text/plain");
            IOUtils.copy(resourceStream, response.getOutputStream());
            response.flushBuffer();
        }
    }
}
