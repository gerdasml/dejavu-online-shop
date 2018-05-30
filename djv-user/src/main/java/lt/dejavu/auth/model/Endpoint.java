package lt.dejavu.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestMethod;

@Getter
@Setter
@ToString
public class Endpoint {
    private RequestMethod method;
    private String path;
}
