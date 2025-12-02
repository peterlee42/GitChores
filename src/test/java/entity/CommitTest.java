package entity;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommitTest {

    // ---------- 1. Successful constructor ----------
    @Test
    void constructor_setsFieldsCorrectly() {
        String roomId = "room-1";
        int commitId = 10;
        String message = "Cleaned kitchen";
        String userId = "user-1";

        Commit commit = new Commit(roomId, commitId, message, userId);

        assertEquals(roomId, commit.getRoomId());
        assertEquals(commitId, commit.getCommitId());
        assertEquals(message, commit.getMessage());
        assertEquals(userId, commit.getUserId());

        // timestamp should be very close to now
        assertNotNull(commit.getTimestamp());
        assertTrue(commit.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    // ---------- 2. Validation errors (branch tests) ----------

    @Test
    void constructor_throwsForNullRoomId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commit(null, 1, "msg", "user"));
    }

    @Test
    void constructor_throwsForEmptyRoomId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commit("   ", 1, "msg", "user"));
    }

    @Test
    void constructor_throwsForNullMessage() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commit("room", 1, null, "user"));
    }

    @Test
    void constructor_throwsForEmptyMessage() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commit("room", 1, "   ", "user"));
    }

    @Test
    void constructor_throwsForNullUserId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commit("room", 1, "msg", null));
    }

    @Test
    void constructor_throwsForEmptyUserId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Commit("room", 1, "msg", "   "));
    }

    // ---------- 3. Overloaded constructor ----------
    @Test
    void overloadedConstructor_setsAllFields() {
        String roomId = "room-1";
        int commitId = 5;
        String message = "Sweep floor";
        String userId = "user-3";
        LocalDateTime ts = LocalDateTime.now().minusHours(1);

        Commit commit = new Commit(roomId, commitId, message, userId, ts);

        assertEquals(roomId, commit.getRoomId());
        assertEquals(commitId, commit.getCommitId());
        assertEquals(message, commit.getMessage());
        assertEquals(userId, commit.getUserId());
        assertEquals(ts, commit.getTimestamp());
    }

    // ---------- 4. fromDynamo ----------
    @Test
    void fromDynamo_buildsCommitCorrectly() {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("roomId", AttributeValue.fromS("abc"));
        item.put("commitId", AttributeValue.fromN("12"));
        item.put("message", AttributeValue.fromS("Did chores"));
        item.put("userId", AttributeValue.fromS("userX"));
        item.put("timestamp", AttributeValue.fromS("2023-11-20T10:15:30"));

        Commit commit = Commit.fromDynamo(item);

        assertEquals("abc", commit.getRoomId());
        assertEquals(12, commit.getCommitId());
        assertEquals("Did chores", commit.getMessage());
        assertEquals("userX", commit.getUserId());
        assertEquals(LocalDateTime.parse("2023-11-20T10:15:30"), commit.getTimestamp());
    }
}
