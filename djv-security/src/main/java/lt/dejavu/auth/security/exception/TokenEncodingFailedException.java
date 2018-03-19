package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.ApiSecurityException;

public class TokenEncodingFailedException extends ApiSecurityException {
    public TokenEncodingFailedException(String message) {
        super(message);
    }

    public TokenEncodingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
