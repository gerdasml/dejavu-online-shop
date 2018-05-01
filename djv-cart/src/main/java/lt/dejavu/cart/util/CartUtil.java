package lt.dejavu.cart.util;

import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.order.model.db.OrderItem;

import java.util.Optional;

public class CartUtil {
    public static Optional<OrderItem> getOrderItemByProductId(Cart cart, long productId) {
        return cart.getItems()
                   .stream()
                   .filter(item -> item.getProduct().getId() == productId)
                   .findFirst();
    }
}
