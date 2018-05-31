package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.db.ShippingInformation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDto {
    private long id;
    private OrderStatus status;
    private boolean reviewShown;
    private Timestamp createdDate;
    private UserDto user;
    private List<OrderItemDto> items;
    private BigDecimal total;
    private ShippingInformation shippingInformation;
    private ReviewDto review;
    private Timestamp lastModified;
}
