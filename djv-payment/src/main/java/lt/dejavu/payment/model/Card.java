package lt.dejavu.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Card {
    private String number;
    private String holder;
    private String cvv;
    private Expiration expiration;
}
