package interface_adapter;

/**
 * Model for tracking the current user session.
 */
public class SessionModel extends ViewModel<String> {

    private String roomId;
    private String userId;
    private String username;

    public SessionModel() {
        super("session");
    }

    public String getRoomId() {
        return roomId;
    }
    public String getUserId() { return userId; }
    public String getUsername() { return username; }

    /**
     * Sets the current room ID.
     *
     * @param roomId the room ID
     */
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * Sets the current user ID.
     *
     * @param userId the user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
        this.firePropertyChange();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Clears the session (for logout).
     */
    public void clearSession() {
        this.roomId = null;
        this.userId = null;
        this.username = null;
        this.firePropertyChange();
    }
}
