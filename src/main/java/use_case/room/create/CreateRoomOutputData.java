package use_case.room.create;

public class CreateRoomOutputData {
    private final String roomName;
    private final String roomDescription;
    private final String inviteCode;

    /**
     * Constructs output data for room creation.
     *
     * @param roomName        the room name
     * @param roomDescription the room description
     * @param inviteCode      the generated invite code
     */
    public CreateRoomOutputData(String roomName, String roomDescription, String inviteCode) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.inviteCode = inviteCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getInviteCode() {
        return inviteCode;
    }
}
