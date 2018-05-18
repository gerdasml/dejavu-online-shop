package lt.dejavu.order.mapper;

import lt.dejavu.order.dto.ReviewDto;
import lt.dejavu.order.model.db.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDto map(Review review) {
        if (review == null) return null;
        ReviewDto dto = new ReviewDto();
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());

        return dto;
    }
}
