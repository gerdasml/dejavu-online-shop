package lt.dejavu.auth.security.exception;

public class BadTokenSignatureException extends Exception {
    public BadTokenSignatureException(String message) {
        super(message);
    }
}
