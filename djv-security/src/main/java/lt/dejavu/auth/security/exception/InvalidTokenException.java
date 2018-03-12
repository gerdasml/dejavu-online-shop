package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.SecurityException;

public class InvalidTokenException extends SecurityException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
