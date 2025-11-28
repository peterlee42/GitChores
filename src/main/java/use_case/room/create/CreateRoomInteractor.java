package use_case.room.create;

import java.security.SecureRandom;

import entity.DomainIdGenerator;
import entity.Room;
import use_case.room.RoomDataAccessInterface;

public class CreateRoomInteractor implements CreateRoomInputBoundary {
    private static final int INVITE_CODE_LENGTH = 6;
    private static final String INVITE_CODE_CHARS = "0123456789";

    private final RoomDataAccessInterface roomDataAccess;
    private final CreateRoomOutputBoundary createRoomPresenter;
    private final SecureRandom random;

    /**
     * Constructs a CreateRoomInteractor.
     *
     * @param roomDataAccess      the room data access
     * @param createRoomPresenter the output boundary
     */
    public CreateRoomInteractor(RoomDataAccessInterface roomDataAccess,
            CreateRoomOutputBoundary createRoomPresenter) {
        this.roomDataAccess = roomDataAccess;
        this.createRoomPresenter = createRoomPresenter;
        this.random = new SecureRandom();
    }

    @Override
    public void execute(CreateRoomInputData inputData) {
        if (inputData.getRoomName() == null || inputData.getRoomName().trim().isEmpty()) {
            createRoomPresenter.presentFailure("Room name cannot be empty");
            return;
        }

        if (inputData.getOwnerId() == null || inputData.getOwnerId().trim().isEmpty()) {
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
                inputData.getOwnerId(),
                inviteCode);

        roomDataAccess.saveRoom(room);

        // Add owner to room
        roomDataAccess.addUserToRoom(roomId, inputData.getOwnerId());

        // Success Confirmation
        final CreateRoomOutputData outputData = new CreateRoomOutputData(
                roomId,
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
