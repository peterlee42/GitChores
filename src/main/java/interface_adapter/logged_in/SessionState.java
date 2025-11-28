package interface_adapter.logged_in;

public class SessionState {
    private String username;
    private String userId;
    private String email;
    private String roomId;
    private String sessionError;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSessionError(String sessionError) {
        this.sessionError = sessionError;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getSessionError() {
        return sessionError;
    }

    @Override
    public String toString() {
        return "SessionState{"
                + "userId='" + userId + '\''
                + ", roomId='" + roomId + '\''
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", sessionError='" + sessionError + '\''
                + '}';
    }
}
