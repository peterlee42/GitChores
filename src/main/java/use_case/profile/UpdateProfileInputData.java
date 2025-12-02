package use_case.profile;

/**
 * Input data for updating a user's profile.
 */
public class UpdateProfileInputData {

    private final String profilePhotoPath;

    /**
     * Constructs input data for updating a profile.
     *
     * @param profilePhotoPath path to the selected profile image, may be null
     */
    public UpdateProfileInputData(final String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }
}
