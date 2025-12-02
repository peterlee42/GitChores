package use_case.commit;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommitResponseModelTest {

    @Test
    void constructorAndGetters_workAsExpected() {
        // Arrange
        int commitId = 42;
        String message = "Refactor chores feature";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        CommitResponseModel response = new CommitResponseModel(commitId, message, timestamp);

        // Assert
        assertEquals(commitId, response.getCommitId());
        assertEquals(message, response.getMessage());
        assertEquals(timestamp, response.getTimestamp());
        assertNull(response.getViewMessage()); // default should be null
    }

    @Test
    void setViewMessage_setsAndGetsViewMessage() {
        CommitResponseModel response =
                new CommitResponseModel(1, "msg", LocalDateTime.now());

        assertNull(response.getViewMessage());

        String viewMessage = "Commit saved successfully!";
        response.setViewMessage(viewMessage);

        assertEquals(viewMessage, response.getViewMessage());
    }
}