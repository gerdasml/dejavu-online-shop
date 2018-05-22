package lt.dejavu.order;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.ReviewDto;
import lt.dejavu.order.exception.OrderNotEligibleForReviewException;
import lt.dejavu.order.service.OrderService;
import lt.dejavu.order.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("${rest.review}")
public class ReviewApi {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{orderId}")
    public void submitReview(HttpServletRequest request,
                             @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                             @PathVariable("orderId") long orderId,
                             @RequestBody ReviewDto review) throws ApiSecurityException {
        authorizeOrder(authHeader, request, orderId);
        reviewService.submitReview(orderId, review);
    }

    @PostMapping("/{orderId}/reject")
    public void rejectReview(HttpServletRequest request,
                             @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                             @PathVariable("orderId") long orderId) throws ApiSecurityException {
        authorizeOrder(authHeader, request, orderId);
        reviewService.rejectReview(orderId);
    }

    @GetMapping("/")
    public List<OrderDto> getOrdersToReview(HttpServletRequest request,
                                            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        return orderService
                .getUserOrderHistory(userId)
                .stream()
                .filter(reviewService::isOrderEligibleForReview)
                .collect(toList());
    }

    private void authorizeOrder(String authHeader,
                                HttpServletRequest request,
                                long orderId) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        OrderDto order = orderService.getOrderById(orderId);
        if (order.getUser().getId() != userId) {
            throw new AccessDeniedException("This order does not belong to this user");
        }
        if (!reviewService.isOrderEligibleForReview(order)) {
            throw new OrderNotEligibleForReviewException();
        }
    }
}
