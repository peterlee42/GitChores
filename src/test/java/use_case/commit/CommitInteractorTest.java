package use_case.commit;

import entity.Commit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for CommitInteractor to achieve 100% code + branch coverage.
 */
@ExtendWith(MockitoExtension.class)
class CommitInteractorTest {

    @Mock
    private CommitDataAccessInterface commitDataAccess;

    @Mock
    private RoomMetadataDataAccessInterface roomMetadataDataAccess;

    @Mock
    private CommitOutputBoundary commitPresenter;

    @Mock
    private CommitRequestModel request;

    // ---------- 1. Success path ----------

    @Test
    void execute_success_savesCommitAndPresentsSuccess() {
        // Arrange
        String roomId = "room-1";
        String userId = "user-1";
        String message = "Fix dishes chore";

        when(request.getRoomId()).thenReturn(roomId);
        when(request.getUserId()).thenReturn(userId);
        when(request.getMessage()).thenReturn(message);

        when(roomMetadataDataAccess.incrementAndGetLatestCommitId(roomId))
                .thenReturn(42);

        CommitInteractor interactor = new CommitInteractor(
                commitDataAccess,
                roomMetadataDataAccess,
                commitPresenter
        );

        // Act
        interactor.execute(request);

        // Assert: commit saved with correct values
        ArgumentCaptor<Commit> commitCaptor = ArgumentCaptor.forClass(Commit.class);
        verify(commitDataAccess, times(1)).saveCommit(commitCaptor.capture());
        Commit savedCommit = commitCaptor.getValue();

        assertEquals(roomId, savedCommit.getRoomId());
        assertEquals(42, savedCommit.getCommitId());
        assertEquals(message, savedCommit.getMessage());
        assertEquals(userId, savedCommit.getUserId());
        assertNotNull(savedCommit.getTimestamp());

        // Assert: presenter called with response built from commit
        ArgumentCaptor<CommitResponseModel> responseCaptor = ArgumentCaptor.forClass(CommitResponseModel.class);
        verify(commitPresenter, times(1)).presentSuccess(responseCaptor.capture());
        CommitResponseModel response = responseCaptor.getValue();

        assertEquals(42, response.getCommitId());
        assertEquals(message, response.getMessage());
        assertNotNull(response.getTimestamp());

        // And no failure path
        verify(commitPresenter, never()).presentFailure(anyString());
    }

    // ---------- 2. DynamoDbException path ----------

    @Test
    void execute_dynamoDbException_presentsDynamoDbFailure() {
        String roomId = "room-1";
        when(request.getRoomId()).thenReturn(roomId);

        // Mock a DynamoDbException and its message
        DynamoDbException ex = mock(DynamoDbException.class);
        when(ex.getMessage()).thenReturn("DDB is down");

        when(roomMetadataDataAccess.incrementAndGetLatestCommitId(roomId))
                .thenThrow(ex);

        CommitInteractor interactor = new CommitInteractor(
                commitDataAccess,
                roomMetadataDataAccess,
                commitPresenter
        );

        interactor.execute(request);

        verify(commitPresenter, times(1))
                .presentFailure("Commit failed (DynamoDB error): DDB is down");
        verify(commitPresenter, never()).presentSuccess(any());
        verify(commitDataAccess, never()).saveCommit(any());
    }

    // ---------- 3. SdkClientException path ----------

    @Test
    void execute_sdkClientException_presentsAwsClientFailure() {
        String roomId = "room-1";
        String userId = "user-1";
        String message = "Some message";

        when(request.getRoomId()).thenReturn(roomId);
        when(request.getUserId()).thenReturn(userId);
        when(request.getMessage()).thenReturn(message);

        when(roomMetadataDataAccess.incrementAndGetLatestCommitId(roomId))
                .thenReturn(10);

        SdkClientException ex = mock(SdkClientException.class);
        when(ex.getMessage()).thenReturn("Network timeout");

        doThrow(ex).when(commitDataAccess).saveCommit(any(Commit.class));

        CommitInteractor interactor = new CommitInteractor(
                commitDataAccess,
                roomMetadataDataAccess,
                commitPresenter
        );

        interactor.execute(request);

        verify(commitPresenter, times(1))
                .presentFailure("Commit failed (AWS client error): Network timeout");
        verify(commitPresenter, never()).presentSuccess(any());
    }

    // ---------- 4. IllegalArgumentException path ----------

    @Test
    void execute_illegalArgumentException_presentsBadInputFailure() {
        String roomId = "room-1";
        String userId = "user-1";
        String message = "Bad input triggers exception";

        when(request.getRoomId()).thenReturn(roomId);
        when(request.getUserId()).thenReturn(userId);
        when(request.getMessage()).thenReturn(message);

        when(roomMetadataDataAccess.incrementAndGetLatestCommitId(roomId))
                .thenReturn(5);

        doThrow(new IllegalArgumentException("Invalid commit data"))
                .when(commitDataAccess)
                .saveCommit(any(Commit.class));

        CommitInteractor interactor = new CommitInteractor(
                commitDataAccess,
                roomMetadataDataAccess,
                commitPresenter
        );

        interactor.execute(request);

        verify(commitPresenter, times(1))
                .presentFailure("Commit failed (bad input): Invalid commit data");
        verify(commitPresenter, never()).presentSuccess(any());
    }
}
