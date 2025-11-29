package use_case.room.join;

import entity.Room;
import entity.User;
import use_case.exception.JoinRoomFailedException;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;
import use_case.session.SessionDataAccessInterface;

public class JoinRoomInteractor implements JoinRoomInputBoundary {

    private final RoomDataAccessInterface roomDataAccess;
    private final SessionDataAccessInterface sessionDataAccess;
    private final JoinRoomOutputBoundary joinRoomPresenter;
    private final UserService userService;

    public JoinRoomInteractor(RoomDataAccessInterface roomDataAccess,
            SessionDataAccessInterface sessionDataAccess, JoinRoomOutputBoundary joinRoomPresenter,
            UserService userService) {
        this.roomDataAccess = roomDataAccess;
        this.sessionDataAccess = sessionDataAccess;
        this.joinRoomPresenter = joinRoomPresenter;
        this.userService = userService;
    }

    @Override
    public void execute(JoinRoomInputData inputData) {
        final User user = userService.getUser();

        try {
            if (user == null) {
                joinRoomPresenter.presentFailure("User not logged in");
                return;
            }

            if (inputData.getInviteCode() == null || inputData.getInviteCode().trim().isEmpty()) {
                joinRoomPresenter.presentFailure("Invite code cannot be empty");
                return;
            }

            // Check if user is in any room
            if (roomDataAccess.getUserRoomId(user.getId()) != null) {
                joinRoomPresenter.presentFailure("You are already in a room. Leave your current room first.");
                return;
            }

            if (user.getId() == null || user.getId().trim().isEmpty()) {
                joinRoomPresenter.presentFailure("User ID cannot be empty");
                return;
            }

            // Find room by invite code
            final Room room = roomDataAccess.getRoomByInviteCode(inputData.getInviteCode());

            if (room == null) {
                joinRoomPresenter.presentFailure("Invalid invite code");
                return;
            }

            // Check if user is already in the room
            if (roomDataAccess.isUserInRoom(room.getId(), user.getId())) {
                joinRoomPresenter.presentFailure("You are already a member of this room");
                return;
            }

            // Add user to room
            roomDataAccess.addUserToRoom(room.getId(), user.getId());

            // Present success
            final JoinRoomOutputData outputData = new JoinRoomOutputData(room.getName(), true, null);
            joinRoomPresenter.presentSuccess(outputData);
        } catch (JoinRoomFailedException ex) {
            joinRoomPresenter.presentFailure("Failed to join create room: " + ex.getMessage());
        }
    }

    @Override
    public void switchToCreateView() {
        joinRoomPresenter.switchToCreateView();
    }

    @Override
    public void switchToLoginView() {
        sessionDataAccess.clearCurrentToken();
        joinRoomPresenter.switchToLoginView();
    }
}
