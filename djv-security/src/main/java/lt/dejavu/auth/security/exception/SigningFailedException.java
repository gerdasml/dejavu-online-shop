package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.ApiSecurityException;

public class SigningFailedException extends ApiSecurityException {
    public SigningFailedException(String message) {
        super(message);
    }

    public SigningFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
