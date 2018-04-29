package lt.dejavu.payment.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {
    private String location;
    private String message;

    public ValidationError(String location, String message) {
        this.location = location;
        this.message = message;
    }
}
