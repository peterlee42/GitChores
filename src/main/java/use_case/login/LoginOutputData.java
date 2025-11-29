package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final Boolean inRoom;

    public LoginOutputData(String username, Boolean inRoom) {
        this.username = username;
        this.inRoom = inRoom;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isInRoom() {
        return inRoom;
    }
}
