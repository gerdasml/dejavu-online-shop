package lt.dejavu.cart.service;

import lt.dejavu.cart.dto.CartDto;

public interface CartService {
    CartDto getCart(long userId);

    void addToCart(long userId, long productId, int amount);

    void updateAmount(long userId, long productId, int amount);

    void removeProduct(long userId, long productId);
}
