package lt.dejavu.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private String error;
    private String message;
}
