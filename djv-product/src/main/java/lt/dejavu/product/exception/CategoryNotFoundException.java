package lt.dejavu.product.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }


    public CategoryNotFoundException(long id) {
        super("cannot find category with id " + id);
    }

}
