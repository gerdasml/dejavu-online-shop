package lt.dejavu.order.service;

import lt.dejavu.order.dto.OrderDto;
import lt.dejavu.order.dto.ReviewDto;
import lt.dejavu.order.mapper.ReviewMapper;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(OrderRepository orderRepository, ReviewMapper reviewMapper) {
        this.orderRepository = orderRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public void submitReview(long orderId, ReviewDto review) {
        orderRepository.addReview(orderId, reviewMapper.map(review));
    }

    @Override
    public void rejectReview(long orderId) {
        orderRepository.addReview(orderId, null);
    }

    @Override
    public boolean isOrderEligibleForReview(OrderDto order) {
        return order != null &&
               order.getStatus() == OrderStatus.DELIVERED &&
               !order.isReviewShown();
    }
}
