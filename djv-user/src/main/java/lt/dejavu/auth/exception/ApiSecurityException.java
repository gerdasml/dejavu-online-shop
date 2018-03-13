package lt.dejavu.auth.exception;

public abstract class ApiSecurityException extends Exception {
    public ApiSecurityException(String message) {
        super(message);
    }

    public ApiSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
