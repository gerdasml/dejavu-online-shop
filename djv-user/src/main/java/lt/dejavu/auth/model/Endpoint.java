package lt.dejavu.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMethod;

@Getter
@Setter
public class Endpoint {
    private RequestMethod method;
    private String path;
}