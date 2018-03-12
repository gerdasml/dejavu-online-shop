package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.SecurityException;

public class TokenEncodingFailedException extends SecurityException {
    public TokenEncodingFailedException(String message) {
        super(message);
    }

    public TokenEncodingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
