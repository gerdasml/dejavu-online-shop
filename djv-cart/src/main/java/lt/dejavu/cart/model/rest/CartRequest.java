package lt.dejavu.cart.model.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartRequest {
    private long productId;
    private int amount;
}
