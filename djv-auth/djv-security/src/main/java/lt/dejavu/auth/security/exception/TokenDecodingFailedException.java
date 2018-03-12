package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.SecurityException;

public class TokenDecodingFailedException extends SecurityException {
    public TokenDecodingFailedException(String message) {
        super(message);
    }

    public TokenDecodingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
