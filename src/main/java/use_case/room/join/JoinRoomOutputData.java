package use_case.room.join;

public class JoinRoomOutputData {
    private final String roomId;
    private final String roomName;
    private final boolean success;
    private final String errorMessage;

    /**
     * Constructs output data for joining a room.
     *
     * @param roomId       the room ID
     * @param roomName     the room name
     * @param success      whether joining was successful
     * @param errorMessage error message if unsuccessful
     */
    public JoinRoomOutputData(String roomId, String roomName, boolean success, String errorMessage) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}