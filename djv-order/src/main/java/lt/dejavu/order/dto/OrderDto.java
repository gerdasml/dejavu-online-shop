package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.order.model.OrderStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private OrderStatus status;
    private Date createdDate;
    private UserDto userDto;
    private List<OrderItemDto> items;
}
