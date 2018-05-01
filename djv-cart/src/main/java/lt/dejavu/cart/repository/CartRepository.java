package lt.dejavu.cart.repository;

import lt.dejavu.auth.model.db.User;
import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.order.model.db.OrderItem;
import lt.dejavu.product.model.Product;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> getCartByUserId(long userId);

    Optional<OrderItem> getOrderItemByProductId(Cart cart, long productId);

    void removeItem(Cart cart, long productId);

    void addOrderItem(Cart cart, Product product, int amount);

    void updateOrderItem(OrderItem item);

    Cart createEmptyCart(User user);

    void deleteCart(long userId);
}
