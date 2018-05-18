package lt.dejavu.product.exception;

public class ProductPropertyNotFoundException extends RuntimeException {
    public ProductPropertyNotFoundException(String message) {
        super(message);
    }

    public ProductPropertyNotFoundException(long id) {
        super("cannot find property with id " + id);
    }

}
