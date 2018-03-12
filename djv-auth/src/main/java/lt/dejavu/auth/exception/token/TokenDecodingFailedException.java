package lt.dejavu.auth.exception.token;

public class TokenDecodingFailedException extends Exception{
    public TokenDecodingFailedException(String message) {
        super(message);
    }

    public TokenDecodingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
