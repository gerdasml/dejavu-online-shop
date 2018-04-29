package lt.dejavu.order.model.rest;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.order.model.OrderStatus;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
