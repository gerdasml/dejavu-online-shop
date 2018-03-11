package lt.dejavu.auth.exception.token;

public class TokenDecodignFailedException extends Exception{
    public TokenDecodignFailedException(String message) {
        super(message);
    }

    public TokenDecodignFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
