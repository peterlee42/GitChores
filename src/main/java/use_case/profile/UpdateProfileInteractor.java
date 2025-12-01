package use_case.profile;

import entity.User;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;
import use_case.session.SessionDataAccessInterface;

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
    private final SessionDataAccessInterface sessionDataAccessObject;
    private final RoomDataAccessInterface roomDataAccessInterface;
    private final UserService userService;

    /**
     * Constructs an interactor with the given presenter.
     *
     * @param presenter               output boundary used to present results
     * @param sessionDataAccessObject data access object for session data
     * @param roomDataAccessInterface data access object for room data
     * @param userService             service for user
     */
    public UpdateProfileInteractor(final UpdateProfileOutputBoundary presenter,
            SessionDataAccessInterface sessionDataAccessObject, RoomDataAccessInterface roomDataAccessInterface,
            UserService userService) {
        this.presenter = presenter;
        this.sessionDataAccessObject = sessionDataAccessObject;
        this.roomDataAccessInterface = roomDataAccessInterface;
        this.userService = userService;
    }

    @Override
    public void updateProfile(final UpdateProfileInputData data) {
        // Later: call DAOs here to update User and profile photo path.

        final String email = data.getEmail();
        final String photoPath = data.getProfilePhotoPath();

        final String message = "Profile updated.";
        final UpdateProfileOutputData output = new UpdateProfileOutputData(message, email, photoPath);

        presenter.prepareSuccessView(output);
    }

    @Override
    public void logout() {
        sessionDataAccessObject.clearCurrentToken();
        presenter.prepareLoginView();
    }

    @Override
    public void leaveRoom() {
        final User user = userService.getUser();
        final String roomId = roomDataAccessInterface.getUserRoomId(user.getId());
        roomDataAccessInterface.removeUserFromRoom(roomId, user.getId());
        presenter.prepareLeaveRoomView();
    }
}
