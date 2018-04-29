package lt.dejavu.cart.repository;

import lt.dejavu.cart.model.db.Cart;

public interface CartRepository {
    Cart getCartByUserId(long userId);

    Cart createEmptyCart(long userId);

    void deleteCart(long userId);
}
