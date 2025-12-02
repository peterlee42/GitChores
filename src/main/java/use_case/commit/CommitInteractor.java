package use_case.commit;

import entity.Commit;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

/**
 * The commit interactor.
 */
public class CommitInteractor implements CommitInputBoundary {

    private final CommitDataAccessInterface commitDataAccess;
    private final RoomMetadataDataAccessInterface roomMetadataDataAccess;
    private final CommitOutputBoundary commitPresenter;

    public CommitInteractor(CommitDataAccessInterface commitDataAccess,
            RoomMetadataDataAccessInterface roomMetadataDataAccess,
            CommitOutputBoundary commitPresenter) {
        this.commitDataAccess = commitDataAccess;
        this.roomMetadataDataAccess = roomMetadataDataAccess;
        this.commitPresenter = commitPresenter;
    }

    @Override
    public void execute(CommitRequestModel request) {
        try {
            // Step 1. Generate the next available commitID using our Room Metadata table
            final int commitId = roomMetadataDataAccess.incrementAndGetLatestCommitId(request.getRoomId());

            // 2. Create a commit entity with our new data
            final Commit commit = new Commit(request.getRoomId(), commitId, request.getMessage(), request.getUserId());

            // 3. Save commit to database
            commitDataAccess.saveCommit(commit);

            // 4. Build out response model
            final CommitResponseModel response = new CommitResponseModel(commit.getCommitId(),
                    commit.getMessage(),
                    commit.getTimestamp());

            // 5. Present the success using commit presenter
            commitPresenter.presentSuccess(response);

        }
        // Catch exceptions (had to replace generic ones with these specific ones)
        catch (DynamoDbException ex) {
            commitPresenter.presentFailure("Commit failed (DynamoDB error): " + ex.getMessage());
        } catch (SdkClientException ex) {
            commitPresenter.presentFailure("Commit failed (AWS client error): " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            commitPresenter.presentFailure("Commit failed (bad input): " + ex.getMessage());
        }
    }
}
