package lt.dejavu.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private int amount;
    private Card card;
    private Expiration expiration;
}
