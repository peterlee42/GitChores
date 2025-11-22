package interface_adapter.commit;

import use_case.commit.CommitInputBoundary;
import use_case.commit.CommitRequestModel;

/**
 * The controller for the commiting to database use case.
 */
public class CommitController {

    private final CommitInputBoundary commitInteractor;

    public CommitController(CommitInputBoundary commitInteractor) {
        this.commitInteractor = commitInteractor;
    }

    /**
     * Called by GitConsoleInteractor when a "git commit -m" command is written.
     * @param roomId the room id of the user creating the commit
     * @param userId the id of the user creating the commit
     * @param message the commit message
     */
    public void execute(String roomId, String userId, String message) {
        final CommitRequestModel request = new CommitRequestModel(roomId, userId, message);
        commitInteractor.execute(request);
    }
}
