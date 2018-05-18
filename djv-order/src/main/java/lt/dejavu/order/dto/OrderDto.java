package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.model.db.Address;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.ReviewStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private long id;
    private OrderStatus status;
    private boolean reviewShown;
    private Timestamp createdDate;
    private UserDto user;
    private List<OrderItemDto> items;
    private BigDecimal total;
    private Address shippingAddress;
    private ReviewDto review;
}
