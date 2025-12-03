package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChoreTest {

    // ---------- 1. Successful constructor ----------
    @Test
    void constructor_setsFieldsCorrectly() {
        String id = "chore-1";
        String roomId = "room-1";
        String assignedUserId = "user-2";
        String creatingUserId = "user-1";
        String title = "Take out trash";
        String description = "Weekly task";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        ChoreStatus status = ChoreStatus.PENDING;
        boolean needsReview = false;

        Chore chore = new Chore(id, roomId, assignedUserId, creatingUserId,
                title, description, dueDate, status, needsReview);

        assertEquals(id, chore.getId());
        assertEquals(roomId, chore.getRoomId());
        assertEquals(assignedUserId, chore.getAssignedUserId());
        assertEquals(creatingUserId, chore.getCreatingUserId());
        assertEquals(title, chore.getTitle());
        assertEquals(description, chore.getDescription());
        assertEquals(dueDate, chore.getDueDate());
        assertEquals(status, chore.getStatus());
        assertEquals(needsReview, chore.getNeedsReview());
        assertNotNull(chore.getCreatedAt());
        assertNotNull(chore.getUpdatedAt());
    }

    // ---------- 2. Validation errors for constructor ----------

    @Test
    void constructor_throwsForNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore(null, "room-1", "user-2", "user-1",
                        "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForEmptyId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("   ", "room-1", "user-2", "user-1",
                        "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForNullRoomId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", null, "user-2", "user-1",
                        "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForEmptyRoomId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", "   ", "user-2", "user-1",
                        "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForNullCreatingUserId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", "room-1", "user-2", null,
                        "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForEmptyCreatingUserId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", "room-1", "user-2", "   ",
                        "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForNullTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", "room-1", "user-2", "user-1",
                        null, "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForEmptyTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", "room-1", "user-2", "user-1",
                        "   ", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForNullDueDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", "room-1", "user-2", "user-1",
                        "title", "desc", null, ChoreStatus.PENDING, false));
    }

    @Test
    void constructor_throwsForNullStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chore("chore-1", "room-1", "user-2", "user-1",
                        "title", "desc", LocalDateTime.now(), null, false));
    }

    @Test
    void constructor_allowsNullAssignedUserId() {
        Chore chore = new Chore("chore-1", "room-1", null, "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        assertNull(chore.getAssignedUserId());
    }

    @Test
    void constructor_allowsNullDescription() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", null, LocalDateTime.now(), ChoreStatus.PENDING, false);

        assertNull(chore.getDescription());
    }

    // ---------- 3. Setter tests ----------

    @Test
    void setAssignedUserId_updatesValue() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        chore.setAssignedUserId("user-3");

        assertEquals("user-3", chore.getAssignedUserId());
    }

    @Test
    void setAssignedUserId_allowsNull() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        chore.setAssignedUserId(null);

        assertNull(chore.getAssignedUserId());
    }

    @Test
    void setTitle_updatesValue() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        chore.setTitle("New Title");

        assertEquals("New Title", chore.getTitle());
    }

    @Test
    void setTitle_throwsForNull() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        assertThrows(IllegalArgumentException.class, () -> chore.setTitle(null));
    }

    @Test
    void setTitle_throwsForEmpty() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        assertThrows(IllegalArgumentException.class, () -> chore.setTitle("   "));
    }

    @Test
    void setDescription_updatesValue() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        chore.setDescription("New description");

        assertEquals("New description", chore.getDescription());
    }

    @Test
    void setDescription_allowsNull() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        chore.setDescription(null);

        assertNull(chore.getDescription());
    }

    @Test
    void setDueDate_updatesValue() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        LocalDateTime newDate = LocalDateTime.now().plusDays(7);
        chore.setDueDate(newDate);

        assertEquals(newDate, chore.getDueDate());
    }

    @Test
    void setDueDate_throwsForNull() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        assertThrows(IllegalArgumentException.class, () -> chore.setDueDate(null));
    }

    @Test
    void setStatus_updatesValue() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        chore.setStatus(ChoreStatus.COMPLETED);

        assertEquals(ChoreStatus.COMPLETED, chore.getStatus());
    }

    @Test
    void setStatus_throwsForNull() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        assertThrows(IllegalArgumentException.class, () -> chore.setStatus(null));
    }

    @Test
    void setNeedsReview_updatesValue() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        chore.setNeedsReview(true);

        assertTrue(chore.getNeedsReview());
    }

    // ---------- 4. Test all ChoreStatus enum values ----------

    @Test
    void constructor_acceptsInactiveStatus() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.INACTIVE, false);

        assertEquals(ChoreStatus.INACTIVE, chore.getStatus());
    }

    @Test
    void constructor_acceptsPendingStatus() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.PENDING, false);

        assertEquals(ChoreStatus.PENDING, chore.getStatus());
    }

    @Test
    void constructor_acceptsCompletedStatus() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.COMPLETED, false);

        assertEquals(ChoreStatus.COMPLETED, chore.getStatus());
    }

    @Test
    void constructor_acceptsReviewPendingStatus() {
        Chore chore = new Chore("chore-1", "room-1", "user-2", "user-1",
                "title", "desc", LocalDateTime.now(), ChoreStatus.REVIEW_PENDING, false);

        assertEquals(ChoreStatus.REVIEW_PENDING, chore.getStatus());
    }
}
