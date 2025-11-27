package use_case.room.join;

public class JoinRoomInputData {
    private final String inviteCode;
    private final String userId;

    /**
     * Constructs input data for joining a room.
     *
     * @param inviteCode the room invite code
     * @param userId     the ID of the user joining
     */
    public JoinRoomInputData(String inviteCode, String userId) {
        this.inviteCode = inviteCode;
        this.userId = userId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public String getUserId() {
        return userId;
    }
}
