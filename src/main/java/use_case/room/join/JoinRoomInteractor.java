package use_case.room.join;

import entity.Room;
import use_case.room.RoomDataAccessInterface;

public class JoinRoomInteractor implements JoinRoomInputBoundary {

    private final RoomDataAccessInterface roomDataAccess;
    private final JoinRoomOutputBoundary outputBoundary;

    public JoinRoomInteractor(RoomDataAccessInterface roomDataAccess, JoinRoomOutputBoundary outputBoundary) {
        this.roomDataAccess = roomDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(JoinRoomInputData inputData) {
        if (inputData.getInviteCode() == null || inputData.getInviteCode().trim().isEmpty()) {
            outputBoundary.presentFailure("Invite code cannot be empty");
            return;
        }

        if (inputData.getUserId() == null || inputData.getUserId().trim().isEmpty()) {
            outputBoundary.presentFailure("User ID cannot be empty");
            return;
        }

        // Find room by invite code
        final Room room = roomDataAccess.getRoomByInviteCode(inputData.getInviteCode());

        if (room == null) {
            outputBoundary.presentFailure("Invalid invite code");
            return;
        }

        // Check if user is already in the room
        if (roomDataAccess.isUserInRoom(room.getId(), inputData.getUserId())) {
            outputBoundary.presentFailure("You are already a member of this room");
            return;
        }

        // Add user to room
        roomDataAccess.addUserToRoom(room.getId(), inputData.getUserId());

        // Present success
        final JoinRoomOutputData outputData = new JoinRoomOutputData(
                room.getId(),
                room.getName(),
                true,
                null
        );
        outputBoundary.presentSuccess(outputData);
    }
}
