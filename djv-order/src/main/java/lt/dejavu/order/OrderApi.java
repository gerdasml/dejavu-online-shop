package lt.dejavu.order;

import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.model.rest.UpdateOrderStatusRequest;
import lt.dejavu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${rest.order}")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{userId}/history")
    public List<OrderDto> getUserOrderHistory(@PathVariable("userId") long userId) {
        return orderService.getUserOrderHistory(userId);
    }

    @GetMapping("/")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable("orderId") long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrderStatus(@PathVariable("orderId") long orderId, @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, request.getStatus());
    }
}
