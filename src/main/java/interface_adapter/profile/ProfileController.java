package interface_adapter.profile;

import use_case.profile.UpdateProfileInputBoundary;
import use_case.profile.UpdateProfileInputData;

/**
 * Controller for the Profile view.
 * Receives UI input and calls the UpdateProfile use case.
 */
public class ProfileController {

    private final UpdateProfileInputBoundary interactor;

    /**
     * Constructs a ProfileController.
     *
     * @param interactor the profile update use case interactor
     */
    public ProfileController(final UpdateProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Saves the profile information.
     *
     * @param email            the current email address
     * @param profilePhotoPath the path to the profile photo; may be null
     */
    public void saveProfile(final String email, final String profilePhotoPath) {
        final UpdateProfileInputData data = new UpdateProfileInputData(email, profilePhotoPath);
        interactor.updateProfile(data);
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        interactor.logout();
    }

    /**
     * Leaves the current room.
     */
    public void leaveRoom() {
        interactor.leaveRoom();
    }
}
