package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.SecurityException;

public class BadTokenSignatureException extends SecurityException {
    public BadTokenSignatureException(String message) {
        super(message);
    }
}
