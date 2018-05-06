package lt.dejavu.cart.model.rest;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.model.db.Address;
import lt.dejavu.payment.model.Card;

@Getter
@Setter
public class CheckoutRequest {
    private Card card;
    private Address shippingAddress;
}
