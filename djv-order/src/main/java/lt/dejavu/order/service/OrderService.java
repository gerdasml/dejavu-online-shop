package lt.dejavu.order.service;

import lt.dejavu.order.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getUserOrderHistory(long userId);

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(long orderId);

    void updateOrderStatus(long orderId);

    Long createOrder(OrderDto order);
}
