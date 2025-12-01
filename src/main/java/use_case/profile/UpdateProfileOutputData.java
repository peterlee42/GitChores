package use_case.profile;

/**
 * Output data returned after a profile update attempt.
 */
public class UpdateProfileOutputData {

    private final String message;
    private final String email;
    private final String profilePhotoPath;

    /**
     * Constructs output data for a profile update.
     *
     * @param message          feedback message for the user
     * @param email            resulting email
     * @param profilePhotoPath resulting profile photo path
     */
    public UpdateProfileOutputData(final String message,
                                   final String email,
                                   final String profilePhotoPath) {
        this.message = message;
        this.email = email;
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }
}
