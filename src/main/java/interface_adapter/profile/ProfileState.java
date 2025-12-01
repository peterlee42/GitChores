package interface_adapter.profile;

public class ProfileState {
    private String username;
    private String email;
    private String profilePhotoPath;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public String getUsername() {
        return username;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
