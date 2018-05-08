package lt.dejavu.order.service;

import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.OrderSummaryDto;
import lt.dejavu.order.model.OrderStatus;

import java.util.List;

public interface OrderService {
    List<OrderDto> getUserOrderHistory(long userId);

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(long orderId);

    void updateOrderStatus(long orderId, OrderStatus status);

    Long createOrder(OrderDto order);

    List<OrderSummaryDto> getOrderSummary();
}
