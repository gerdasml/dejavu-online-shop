package lt.dejavu.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Expiration {
    private int year;
    private int month;
}
