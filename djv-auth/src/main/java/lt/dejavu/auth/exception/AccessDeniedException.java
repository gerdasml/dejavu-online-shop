package lt.dejavu.auth.exception;

public class AccessDeniedException extends SecurityException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
