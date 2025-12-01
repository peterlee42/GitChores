package use_case.exception;

public class ChoreCreationFailedException extends RuntimeException {
    public ChoreCreationFailedException(String message) {
        super(message);
    }
}
