package lt.dejavu.cart.model.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.auth.model.db.Address;
import lt.dejavu.order.model.db.ShippingInformation;
import lt.dejavu.payment.model.Card;

@Getter
@Setter
@ToString
public class CheckoutRequest {
    private Card card;
    private ShippingInformation shippingInformation;
}
