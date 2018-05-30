package lt.dejavu.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Payment {
    private int amount;
    private Card card;
}
