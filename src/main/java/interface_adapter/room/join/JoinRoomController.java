package interface_adapter.room.join;

import use_case.room.join.JoinRoomInputBoundary;
import use_case.room.join.JoinRoomInputData;

public class JoinRoomController {

    private final JoinRoomInputBoundary interactor;

    public JoinRoomController(JoinRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the join room use case.
     *
     * @param inviteCode the room invite code
     * @param userId     the user ID
     */
    public void execute(String inviteCode, String userId) {
        final JoinRoomInputData inputData = new JoinRoomInputData(inviteCode, userId);
        interactor.execute(inputData);
    }
}
