package lt.dejavu.auth.security.exception;

import lt.dejavu.auth.exception.ApiSecurityException;

public class BadTokenSignatureException extends ApiSecurityException {
    public BadTokenSignatureException(String message) {
        super(message);
    }
}
