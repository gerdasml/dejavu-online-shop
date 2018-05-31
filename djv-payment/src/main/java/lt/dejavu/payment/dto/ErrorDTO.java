package lt.dejavu.payment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorDTO {
    private String error;
    private String message;
}
