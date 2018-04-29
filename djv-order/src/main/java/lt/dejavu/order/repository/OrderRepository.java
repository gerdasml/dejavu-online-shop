package lt.dejavu.order.repository;

import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.db.Order;

import java.util.List;

public interface OrderRepository {
    Order getOrder(long id);

    List<Order> getOrders();

    List<Order> getOrdersByUser(long userId);

    long saveOrder(Order order);

    void updateOrderStatus(long orderId, OrderStatus status);
}
