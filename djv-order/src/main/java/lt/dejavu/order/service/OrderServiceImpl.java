package lt.dejavu.order.service;

import lt.dejavu.auth.mapper.UserMapper;
import lt.dejavu.auth.model.db.User;
import lt.dejavu.auth.repository.UserRepository;
import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.OrderItemDto;
import lt.dejavu.order.dto.OrderSummaryDto;
import lt.dejavu.order.exception.OrderNotFoundException;
import lt.dejavu.order.mapper.OrderMapper;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.ReviewStatus;
import lt.dejavu.order.model.db.Order;
import lt.dejavu.order.model.db.OrderItem;
import lt.dejavu.order.repository.OrderRepository;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserMapper userMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, ProductRepository productRepository, UserMapper userMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userMapper = userMapper;
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
        Order order = orderRepository.getOrder(orderId);
        if (order == null) {
            throw new OrderNotFoundException("The order with the specified ID was not found");
        }
        return orderMapper.map(order);
    }

    @Override
    public void updateOrderStatus(long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status);
        // TODO: update reviewStatus
    }

    @Override
    public Long createOrder(OrderDto dto) {
        Order order = new Order();
        User user = userRepository.getUserById(dto.getUser().getId());
        order.setUser(user);

        order.setStatus(dto.getStatus());
        order.setCreationDate(dto.getCreatedDate());
        order.setShippingAddress(dto.getShippingAddress());
        order.setReviewShown(dto.isReviewShown());
        List<OrderItem> orderItems = dto.getItems()
                                        .stream()
                                        .map(item -> buildOrderItem(item, order))
                                        .collect(toList());

        order.setItems(orderItems);

        orderRepository.saveOrder(order);
        return order.getId();
    }

    @Override
    public List<OrderSummaryDto> getOrderSummary() {
        List<User> users = userRepository.getAllUsers();
        return users.stream().map(this::buildOrderSummary).collect(toList());
    }

    private OrderSummaryDto buildOrderSummary(User user) {
        List<OrderDto> orders = this.getUserOrderHistory(user.getId());
        int count = orders.size();
        BigDecimal total = orders.stream().map(OrderDto::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average =
                count == 0
                ? null
                : total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

        OrderSummaryDto orderSummary = new OrderSummaryDto();
        orderSummary.setUser(userMapper.map(user));
        orderSummary.setOrderCount(count);
        orderSummary.setTotalSpending(total);
        orderSummary.setAverageSpending(average);

        return orderSummary;
    }

    private OrderItem buildOrderItem(OrderItemDto dto, Order order) {
        OrderItem item = new OrderItem();
        item.setAmount(dto.getAmount());
        item.setProduct(productRepository.getProduct(dto.getProduct().getId()));
        item.setOrder(order);
        return item;
    }
}
