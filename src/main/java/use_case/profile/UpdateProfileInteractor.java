package use_case.profile;

/**
 * Interactor for updating a user's profile.
 *
 * <p>
 * For now this does not persist to Cognito/DB; it only echoes back the
 * provided information so the presenter can notify the user. Persistence
 * can be added later without changing the outer layers.
 */
public class UpdateProfileInteractor implements UpdateProfileInputBoundary {

    private final UpdateProfileOutputBoundary presenter;

    /**
     * Constructs an interactor with the given presenter.
     *
     * @param presenter output boundary used to present results
     */
    public UpdateProfileInteractor(final UpdateProfileOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updateProfile(final UpdateProfileInputData data) {
        // Later: call DAOs here to update User and profile photo path.

        final String email = data.getEmail();
        final String photoPath = data.getProfilePhotoPath();

        final String message = "Profile updated.";
        final UpdateProfileOutputData output =
                new UpdateProfileOutputData(message, email, photoPath);

        presenter.prepareSuccessView(output);
    }
}
