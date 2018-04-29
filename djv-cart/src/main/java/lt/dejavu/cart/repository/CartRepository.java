package lt.dejavu.cart.repository;

import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.order.model.db.OrderItem;

public interface CartRepository {
    Cart getCartByUserId(long userId);

    OrderItem getOrderItemByProductId(Cart cart, long productId);

    void removeItem(Cart cart, long productId);

    Cart createEmptyCart(long userId);

    void deleteCart(long userId);
}
