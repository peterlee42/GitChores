package use_case.room.create;

public class CreateRoomOutputData {
    private final String roomId;
    private final String roomName;
    private final String inviteCode;
    private final boolean success;

    /**
     * Constructs output data for room creation.
     *
     * @param roomId     the created room ID
     * @param roomName   the room name
     * @param inviteCode the generated invite code
     * @param success    whether the creation was successful
     */
    public CreateRoomOutputData(String roomId, String roomName, String inviteCode, boolean success) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.inviteCode = inviteCode;
        this.success = success;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public boolean isSuccess() {
        return success;
    }
}
