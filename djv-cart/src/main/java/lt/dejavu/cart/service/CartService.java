package lt.dejavu.cart.service;

import lt.dejavu.auth.model.db.Address;
import lt.dejavu.cart.model.rest.CartResponse;
import lt.dejavu.payment.exception.PaymentException;
import lt.dejavu.payment.model.Card;

public interface CartService {
    CartResponse getCart(long userId);

    CartResponse addToCart(long userId, long productId, int amount);

    CartResponse updateAmount(long userId, long productId, int amount);

    CartResponse removeProduct(long userId, long productId);

    void checkout(long userId, Card cardInfo, Address shippingAddress) throws PaymentException;
}
