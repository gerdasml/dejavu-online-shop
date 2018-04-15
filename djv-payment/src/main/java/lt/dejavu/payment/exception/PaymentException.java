package lt.dejavu.payment.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PaymentException extends Exception {
    private HttpStatus responseStatus;

    public PaymentException(HttpStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }
}
