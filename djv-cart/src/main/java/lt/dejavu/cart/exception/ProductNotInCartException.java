package lt.dejavu.cart.exception;

public class ProductNotInCartException extends RuntimeException {
    public ProductNotInCartException(String message) {
        super(message);
    }
}
