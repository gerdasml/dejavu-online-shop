package lt.dejavu.order;

import lt.dejavu.auth.exception.ApiSecurityException; 
import lt.dejavu.auth.model.Endpoint; 
import lt.dejavu.auth.service.SecurityService; 
import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.OrderSummaryDto;
import lt.dejavu.order.model.rest.UpdateOrderStatusRequest;
import lt.dejavu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, 
                                              @PathVariable("userId") long userId) throws ApiSecurityException { 
        authorize(authHeader, request); 
        return orderService.getUserOrderHistory(userId); 
    } 
 
    @GetMapping("/history") 
    public List<OrderDto> getOrderHistory(HttpServletRequest request, 
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws ApiSecurityException { 
        long userId = authorize(authHeader, request);
        return orderService.getUserOrderHistory(userId);
    }

    @GetMapping("/")
    public List<OrderDto> getAllOrders(HttpServletRequest request, 
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws ApiSecurityException { 
        authorize(authHeader, request);
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(HttpServletRequest request, 
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, 
                                 @PathVariable("orderId") long orderId) throws ApiSecurityException { 
        // TODO: should a user be able to access this? 
        authorize(authHeader, request); 
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrderStatus(HttpServletRequest request, 
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, 
                                  @PathVariable("orderId") long orderId, 
                                  @RequestBody UpdateOrderStatusRequest updateRequest) throws ApiSecurityException { 
        authorize(authHeader, request); 
        orderService.updateOrderStatus(orderId, updateRequest.getStatus()); 
    }

    @GetMapping("/summary")
    public List<OrderSummaryDto> getOrderSummary(HttpServletRequest request,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
                                                ) throws ApiSecurityException {
        authorize(authHeader, request);
        return orderService.getOrderSummary();
    }
 
    private Endpoint buildEndpoint(HttpServletRequest request) { 
        Endpoint endpoint = new Endpoint(); 
        endpoint.setMethod(RequestMethod.valueOf(request.getMethod())); 
        endpoint.setPath(request.getRequestURI()); 
        return endpoint; 
    } 
 
    private long authorize(String authHeader, HttpServletRequest request) throws ApiSecurityException { 
        return securityService.authorize(authHeader, buildEndpoint(request));
    }
}
