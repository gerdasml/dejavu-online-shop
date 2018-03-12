package lt.dejavu.auth.security.exception;

public class TokenEncodingFailedException extends Exception {
    public TokenEncodingFailedException(String message) {
        super(message);
    }

    public TokenEncodingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
