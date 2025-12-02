package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    // ---------- 1. Successful constructor ----------
    @Test
    void constructor_setsFieldsCorrectly() {
        final String id = "room-1";
        final String name = "My Room";
        final String description = "A nice place";
        final String ownerId = "owner-1";
        final String inviteCode = "123456";

        final Room room = new Room(id, name, description, ownerId, inviteCode);

        assertEquals(id, room.getId());
        assertEquals(name, room.getName());
        assertEquals(description, room.getDescription());
        assertEquals(ownerId, room.getOwnerId());
        assertEquals(inviteCode, room.getInviteCode());

        assertNotNull(room.getCreatedAt());
        assertNotNull(room.getUpdatedAt());
        assertTrue(room.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void constructor_allowsNullDescription() {
        final String id = "room-2";
        final String name = "Another Room";
        final String ownerId = "owner-2";
        final String inviteCode = "654321";

        final Room room = new Room(id, name, null, ownerId, inviteCode);

        assertNull(room.getDescription());
        assertEquals(name, room.getName());
        assertEquals(ownerId, room.getOwnerId());
    }

    // ---------- 2. Validation errors (branch tests) ----------

    @Test
    void constructor_throwsForNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room(null, "name", "desc", "owner", "123"));
    }

    @Test
    void constructor_throwsForEmptyId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("   ", "name", "desc", "owner", "123"));
    }

    @Test
    void constructor_throwsForNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("rid", null, "desc", "owner", "123"));
    }

    @Test
    void constructor_throwsForEmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("rid", "   ", "desc", "owner", "123"));
    }

    @Test
    void constructor_throwsForNullOwnerId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("rid", "name", "desc", null, "123"));
    }

    @Test
    void constructor_throwsForEmptyOwnerId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("rid", "name", "desc", "   ", "123"));
    }

    @Test
    void constructor_throwsForNullInviteCode() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("rid", "name", "desc", "owner", null));
    }

    @Test
    void constructor_throwsForEmptyInviteCode() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("rid", "name", "desc", "owner", "   "));
    }

    // ---------- 3. Mutators (setters) ----------
    @Test
    void setters_updateNameAndDescription() {
        final Room room = new Room("room-3", "Initial", "first desc", "owner-3", "000111");

        room.setName("Updated Name");
        room.setDescription("Updated description");

        assertEquals("Updated Name", room.getName());
        assertEquals("Updated description", room.getDescription());
    }
}

