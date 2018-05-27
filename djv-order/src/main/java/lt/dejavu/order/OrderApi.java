package lt.dejavu.order;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.OrderSummaryDto;
import lt.dejavu.order.model.rest.UpdateOrderStatusRequest;
import lt.dejavu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${rest.order}")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/{userId}/history")
    public List<OrderDto> getUserOrderHistory(HttpServletRequest request,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                              @PathVariable("userId") long userId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return orderService.getUserOrderHistory(userId);
    }

    @GetMapping("/history")
    public List<OrderDto> getOrderHistory(HttpServletRequest request,
                                          @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        return orderService.getUserOrderHistory(userId);
    }

    @GetMapping("/")
    public List<OrderDto> getAllOrders(HttpServletRequest request,
                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(HttpServletRequest request,
                                 @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                 @PathVariable("orderId") long orderId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrderStatus(HttpServletRequest request,
                                  @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                  @RequestHeader(value = HttpHeaders.IF_MATCH) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModified,
                                  @PathVariable("orderId") long orderId,
                                  @RequestBody UpdateOrderStatusRequest updateRequest) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        orderService.updateOrderStatus(orderId, lastModified.toInstant(), updateRequest.getStatus());
    }

    @GetMapping("/summary")
    public List<OrderSummaryDto> getOrderSummary(HttpServletRequest request,
                                                 @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader
                                                ) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return orderService.getOrderSummary();
    }
}
