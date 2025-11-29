package use_case.exception;

public class TokenFailedException extends RuntimeException {
    public TokenFailedException(String message) {
        super(message);
    }

    public TokenFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
