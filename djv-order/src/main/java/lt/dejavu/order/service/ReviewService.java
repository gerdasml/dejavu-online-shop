package lt.dejavu.order.service;

import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.ReviewDto;

public interface ReviewService {
    void submitReview(long orderId, ReviewDto review);
    void rejectReview(long orderId);
    boolean isOrderEligibleForReview(OrderDto order);
}
