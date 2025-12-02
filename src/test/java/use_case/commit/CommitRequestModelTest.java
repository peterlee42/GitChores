package use_case.commit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommitRequestModelTest {

    @Test
    void constructorAndGetters_workAsExpected() {
        // Arrange
        String roomId = "room-1";
        String userId = "user-1";
        String message = "Do the dishes";

        // Act
        CommitRequestModel request = new CommitRequestModel(roomId, userId, message);

        // Assert
        assertEquals(roomId, request.getRoomId());
        assertEquals(userId, request.getUserId());
        assertEquals(message, request.getMessage());
    }
}
