package use_case.profile;

/**
 * Input data for updating a user's profile.
 */
public class UpdateProfileInputData {

    private final String email;
    private final String profilePhotoPath;

    /**
     * Constructs input data for updating a profile.
     *
     * @param email            new email, or existing email if unchanged
     * @param profilePhotoPath path to the selected profile image, may be null
     */
    public UpdateProfileInputData(final String email, final String profilePhotoPath) {
        this.email = email;
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }
}
