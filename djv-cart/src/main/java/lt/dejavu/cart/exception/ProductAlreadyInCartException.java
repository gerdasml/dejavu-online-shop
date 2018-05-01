package lt.dejavu.cart.exception;

public class ProductAlreadyInCartException extends RuntimeException {
    public ProductAlreadyInCartException(String message) {
        super(message);
    }
}
