package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.ApiSecurityException;

public class InvalidTokenException extends ApiSecurityException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
