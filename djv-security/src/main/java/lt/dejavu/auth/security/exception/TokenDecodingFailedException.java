package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.ApiSecurityException;

public class TokenDecodingFailedException extends ApiSecurityException {
    public TokenDecodingFailedException(String message) {
        super(message);
    }

    public TokenDecodingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
