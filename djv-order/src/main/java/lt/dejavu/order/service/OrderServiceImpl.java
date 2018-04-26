package lt.dejavu.order.service;

import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.mapper.OrderMapper;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.db.Order;
import lt.dejavu.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDto> getUserOrderHistory(long userId) {
        return orderMapper.map(orderRepository.getOrdersByUser(userId));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.map(orderRepository.getOrders());
    }

    @Override
    public OrderDto getOrderById(long orderId) {
        return orderMapper.map(orderRepository.getOrder(orderId));
    }

    @Override
    public void updateOrderStatus(long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status);
    }

    @Override
    public Long createOrder(OrderDto dto) {
        Order order = new Order();
        orderRepository.saveOrder(order);
        return null;
    }
}
