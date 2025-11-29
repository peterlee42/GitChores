package use_case.room.create;

import java.security.SecureRandom;

import entity.DomainIdGenerator;
import entity.Room;
import entity.User;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;
import use_case.session.SessionDataAccessInterface;

public class CreateRoomInteractor implements CreateRoomInputBoundary {
    private static final int INVITE_CODE_LENGTH = 6;
    private static final String INVITE_CODE_CHARS = "0123456789";

    private final RoomDataAccessInterface roomDataAccess;
    private final SessionDataAccessInterface sessionDataAccess;
    private final CreateRoomOutputBoundary createRoomPresenter;
    private final SecureRandom random;
    private final UserService userService;

    /**
     * Constructs a CreateRoomInteractor.
     *
     * @param roomDataAccess      the room data access
     * @param sessionDataAccess   the session data access
     * @param createRoomPresenter the output boundary
     * @param userService         the user service
     */
    public CreateRoomInteractor(RoomDataAccessInterface roomDataAccess,
            SessionDataAccessInterface sessionDataAccess,
            CreateRoomOutputBoundary createRoomPresenter, UserService userService) {
        this.roomDataAccess = roomDataAccess;
        this.sessionDataAccess = sessionDataAccess;
        this.createRoomPresenter = createRoomPresenter;
        this.userService = userService;
        this.random = new SecureRandom();
    }

    @Override
    public void execute(CreateRoomInputData inputData) {
        final User user = userService.getUser();

        if (user == null) {
            createRoomPresenter.presentFailure("User not logged in");
            return;
        }

        if (inputData.getRoomName() == null || inputData.getRoomName().trim().isEmpty()) {
            createRoomPresenter.presentFailure("Room name cannot be empty");
            return;
        }

        if (user.getId() == null || user.getId().trim().isEmpty()) {
            createRoomPresenter.presentFailure("Owner ID cannot be empty");
            return;
        }

        // Generate room ID, invite code
        final String roomId = DomainIdGenerator.generateIdWithPrefix("room");
        final String inviteCode = generateInviteCode();

        // Create room entity
        final Room room = new Room(
                roomId,
                inputData.getRoomName(),
                inputData.getDescription(),
                user.getId(),
                inviteCode);

        roomDataAccess.saveRoom(room);

        // Add owner to room
        roomDataAccess.addUserToRoom(roomId, user.getId());

        // Success Confirmation
        final CreateRoomOutputData outputData = new CreateRoomOutputData(
                inputData.getRoomName(),
                inviteCode,
                true);
        createRoomPresenter.presentSuccess(outputData);
    }

    @Override
    public void switchToJoinView() {
        createRoomPresenter.switchToJoinView();
    }

    @Override
    public void switchToLoginView() {
        sessionDataAccess.clearCurrentToken();
        createRoomPresenter.switchToLoginView();
    }

    private String generateInviteCode() {
        String code;
        int attempts = 0;
        final int maxAttempts = 100;

        do {
            final StringBuilder codeBuilder = new StringBuilder(INVITE_CODE_LENGTH);
            for (int i = 0; i < INVITE_CODE_LENGTH; i++) {
                final int index = random.nextInt(INVITE_CODE_CHARS.length());
                codeBuilder.append(INVITE_CODE_CHARS.charAt(index));
            }
            code = codeBuilder.toString();
            attempts++;

            if (attempts >= maxAttempts) {
                throw new RuntimeException("Failed to generate unique invite code after " + maxAttempts + " attempts");
            }
        } while (roomDataAccess.getRoomByInviteCode(code) != null);

        return code;
    }
}
