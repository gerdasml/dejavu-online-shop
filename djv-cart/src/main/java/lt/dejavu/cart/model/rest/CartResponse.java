package lt.dejavu.cart.model.rest;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.order.dto.OrderItemDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponse {
    private UserDto user;
    private List<OrderItemDto> items;
    private BigDecimal total;
}
