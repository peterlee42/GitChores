package main.java.interface_adapter.join;

/**
 * The state for the Join View Model.
 */
public class JoinState {
    private String joinError;
    private String roomCode = "";

    public String getJoinError() {
        return joinError;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setJoinError(String joinError) {
        this.joinError = joinError;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

}
