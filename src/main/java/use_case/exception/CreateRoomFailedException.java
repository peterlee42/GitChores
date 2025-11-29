package use_case.exception;

public class CreateRoomFailedException extends RuntimeException {
    public CreateRoomFailedException(String message) {
        super(message);
    }
}
