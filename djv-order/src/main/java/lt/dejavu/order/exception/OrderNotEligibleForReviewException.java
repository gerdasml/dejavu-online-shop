package lt.dejavu.order.exception;

public class OrderNotEligibleForReviewException extends RuntimeException {
    public OrderNotEligibleForReviewException() {
        super("This order is not eligible for review");
    }

    public OrderNotEligibleForReviewException(String message) {
        super(message);
    }
}
