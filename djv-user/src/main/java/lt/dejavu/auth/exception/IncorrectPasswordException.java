package lt.dejavu.auth.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException() {
        super("The provided password is not correct");
    }
}
