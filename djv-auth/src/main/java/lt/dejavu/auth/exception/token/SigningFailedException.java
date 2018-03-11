package lt.dejavu.auth.exception.token;

public class SigningFailedException extends Exception {
    public SigningFailedException(String message) {
        super(message);
    }

    public SigningFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
