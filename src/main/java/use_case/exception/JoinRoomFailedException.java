package use_case.exception;

public class JoinRoomFailedException extends RuntimeException {
    public JoinRoomFailedException(String message) {
        super(message);
    }
}
