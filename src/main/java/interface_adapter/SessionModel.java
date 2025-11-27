package interface_adapter;

/**
 * Model for tracking the current user session.
 */
public class SessionModel extends ViewModel<String> {

    private String roomId;

    public SessionModel() {
        super("session");
    }

    public String getRoomId() {
        return roomId;
    }

    /**
     * Sets the current room ID.
     *
     * @param roomId the room ID
     */
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * Clears the session (for logout).
     */
    public void clearSession() {
        this.roomId = null;
        this.firePropertyChange();
    }
}
