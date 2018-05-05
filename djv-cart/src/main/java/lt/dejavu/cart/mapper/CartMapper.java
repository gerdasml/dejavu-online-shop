package lt.dejavu.cart.mapper;

import lt.dejavu.auth.mapper.UserMapper;
import lt.dejavu.cart.dto.CartDto;
import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.order.dto.OrderItemDto;
import lt.dejavu.order.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CartMapper {
    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public CartMapper(UserMapper userMapper, OrderItemMapper orderItemMapper) {
        this.userMapper = userMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public CartDto map(Cart cart) {
        CartDto dto = new CartDto();
        List<OrderItemDto> items = cart.getItems()
                                       .stream()
                                       .map(orderItemMapper::map)
                                       .collect(toList());
        dto.setUser(userMapper.map(cart.getUser()));
        dto.setItems(items);
        dto.setTotal(items.stream().map(OrderItemDto::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        return dto;
    }
}
