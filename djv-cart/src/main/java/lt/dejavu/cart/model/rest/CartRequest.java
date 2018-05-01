package lt.dejavu.cart.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private long productId;
    private int amount;
}
