package lt.dejavu.cart.service;

import lt.dejavu.auth.model.db.Address;
import lt.dejavu.cart.dto.CartDto;
import lt.dejavu.payment.exception.PaymentException;
import lt.dejavu.payment.model.Card;

public interface CartService {
    CartDto getCart(long userId);

    CartDto addToCart(long userId, long productId, int amount);

    CartDto updateAmount(long userId, long productId, int amount);

    CartDto removeProduct(long userId, long productId);

    void checkout(long userId, Card cardInfo, Address shippingAddress) throws PaymentException;
}
