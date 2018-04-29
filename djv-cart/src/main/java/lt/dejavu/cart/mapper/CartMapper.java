package lt.dejavu.cart.mapper;

import lt.dejavu.auth.mapper.UserMapper;
import lt.dejavu.cart.dto.CartDto;
import lt.dejavu.cart.model.db.Cart;
import lt.dejavu.order.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        dto.setUser(userMapper.map(cart.getUser()));
        dto.setItems(
            cart.getItems()
                .stream()
                .map(orderItemMapper::map)
                .collect(toList())
        );

        return dto;
    }
}
