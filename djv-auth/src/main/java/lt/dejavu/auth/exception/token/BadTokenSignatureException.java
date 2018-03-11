package lt.dejavu.auth.exception.token;

public class BadTokenSignatureException extends Exception {
    public BadTokenSignatureException(String message) {
        super(message);
    }
}
