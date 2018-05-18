package lt.dejavu.order.mapper;

import lt.dejavu.auth.mapper.UserMapper;
import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.OrderItemDto;
import lt.dejavu.order.model.db.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderMapper {
    private final UserMapper userMapper;
    private final OrderItemMapper itemMapper;
    private final ReviewMapper reviewMapper;

    @Autowired
    public OrderMapper(UserMapper userMapper, OrderItemMapper itemMapper, ReviewMapper reviewMapper) {
        this.userMapper = userMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;
    }

    public OrderDto map(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCreatedDate(order.getCreationDate());
        dto.setStatus(order.getStatus());
        dto.setReviewShown(order.getReviewShown());
        dto.setUser(userMapper.map(order.getUser()));
        dto.setShippingAddress(order.getShippingAddress());
        dto.setReview(reviewMapper.map(order.getReview()));
        List<OrderItemDto> items = itemMapper.map(order.getItems());
        dto.setItems(items);
        dto.setTotal(items.stream().map(OrderItemDto::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        return dto;
    }

    public List<OrderDto> map(List<Order> orders) {
        return orders.stream()
                     .map(this::map)
                     .collect(toList());
    }
}
