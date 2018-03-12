package lt.dejavu.auth.security.exception;

public class SigningFailedException extends Exception {
    public SigningFailedException(String message) {
        super(message);
    }

    public SigningFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
