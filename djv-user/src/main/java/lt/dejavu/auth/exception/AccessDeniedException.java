package lt.dejavu.auth.exception;

public class AccessDeniedException extends ApiSecurityException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
