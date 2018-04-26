package lt.dejavu.order.service;

import lt.dejavu.auth.model.db.User;
import lt.dejavu.auth.repository.UserRepository;
import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.OrderItemDto;
import lt.dejavu.order.mapper.OrderMapper;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.db.Order;
import lt.dejavu.order.model.db.OrderItem;
import lt.dejavu.order.repository.OrderRepository;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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
        if(order == null) {
            // TODO: order not found
        }
        return orderMapper.map(order);
    }

    @Override
    public void updateOrderStatus(long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status);
    }

    @Override
    public Long createOrder(OrderDto dto) {
        Order order = new Order();
        User user = userRepository.getUserById(dto.getUserDto().getId());
        order.setUser(user);

        order.setStatus(dto.getStatus());
        order.setCreationDate(dto.getCreatedDate());
        List<OrderItem> orderItems = dto.getItems()
                                     .stream()
                                     .map(item -> buildOrderItem(item, order))
                                     .collect(toList());

        order.setItems(orderItems);

        orderRepository.saveOrder(order);
        return order.getId();
    }

    private OrderItem buildOrderItem(OrderItemDto dto, Order order) {
        OrderItem item = new OrderItem();
        item.setAmount(dto.getAmount());
        item.setProduct(productRepository.getProduct(dto.getProduct().getId()));
        item.setOrder(order);
        return item;
    }
}
