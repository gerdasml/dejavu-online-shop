package lt.dejavu.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private String number;
    private String holder;
    private String cvv;
}
