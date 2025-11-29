package use_case.room.join;

public class JoinRoomInputData {
    private final String inviteCode;

    /**
     * Constructs input data for joining a room.
     *
     * @param inviteCode the room invite code
     */
    public JoinRoomInputData(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }
}
