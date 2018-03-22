package lt.dejavu.web.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExceptionDetails {
    private Date timestamp;
    private String message;
    private String type;
}
