package use_case.room.create;

import entity.DomainIdGenerator;
import entity.Room;
import use_case.room.RoomDataAccessInterface;

import java.security.SecureRandom;

public class CreateRoomInteractor implements CreateRoomInputBoundary {
    private static final int INVITE_CODE_LENGTH = 6;
    private static final String INVITE_CODE_CHARS = "0123456789";

    private final RoomDataAccessInterface roomDataAccess;
    private final CreateRoomOutputBoundary outputBoundary;
    private final SecureRandom random;

    /**
     * Constructs a CreateRoomInteractor.
     *
     * @param roomDataAccess the room data access
     * @param outputBoundary the output boundary
     */
    public CreateRoomInteractor(RoomDataAccessInterface roomDataAccess,
                                CreateRoomOutputBoundary outputBoundary) {
        this.roomDataAccess = roomDataAccess;
        this.outputBoundary = outputBoundary;
        this.random = new SecureRandom();
    }

    @Override
    public void execute(CreateRoomInputData inputData) {
        try {
            if (inputData.getRoomName() == null || inputData.getRoomName().trim().isEmpty()) {
                outputBoundary.presentFailure("Room name cannot be empty");
                return;
            }

            if (inputData.getOwnerId() == null || inputData.getOwnerId().trim().isEmpty()) {
                outputBoundary.presentFailure("Owner ID cannot be empty");
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
                    inviteCode
            );

            roomDataAccess.saveRoom(room);

            // Add owner to room
            roomDataAccess.addUserToRoom(roomId, inputData.getOwnerId());

            // Success Confirmation
            final CreateRoomOutputData outputData = new CreateRoomOutputData(
                    roomId,
                    inputData.getRoomName(),
                    inviteCode,
                    true
            );
            outputBoundary.presentSuccess(outputData);

        }
        catch (Exception exception) {
            outputBoundary.presentFailure("Failed to create room: " + exception.getMessage());
        }
    }

    private String generateInviteCode() {
        final StringBuilder code = new StringBuilder(INVITE_CODE_LENGTH);
        for (int i = 0; i < INVITE_CODE_LENGTH; i++) {
            final int index = random.nextInt(INVITE_CODE_CHARS.length());
            code.append(INVITE_CODE_CHARS.charAt(index));
        }
        return code.toString();
    }
}
