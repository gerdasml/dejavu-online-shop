package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.SecurityException;

public class SigningFailedException extends SecurityException {
    public SigningFailedException(String message) {
        super(message);
    }

    public SigningFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
