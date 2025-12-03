package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void noPhoto() {
        final User user = new User("123", "john", "john@example.com");

        assertEquals("123", user.getId());
        assertEquals("john", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertNull(user.getProfilePhotoPath());
    }

    @Test
    void photo() {
        final User user = new User("123", "john", "john@example.com", "../resources/logo.png");

        assertEquals("123", user.getId());
        assertEquals("john", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("../resources/logo.png", user.getProfilePhotoPath());
    }

    @Test
    void emptyUsername() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("123", "", "email@example.com"));

        assertEquals("Username cannot be empty", ex.getMessage());
    }

    @Test
    void nullUsername() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("123", null, "email@example.com"));

        assertEquals("Username cannot be empty", ex.getMessage());
    }

    @Test
    void emptyEmail() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("123", "john", ""));

        assertEquals("Email cannot be empty", ex.getMessage());
    }

    @Test
    void nullEmail() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("123", "john", null));

        assertEquals("Email cannot be empty", ex.getMessage());
    }

    @Test
    void testSetters() {
        final User user = new User("123", "peter", "peter@example.com");

        user.setUsername("joe");
        user.setEmail("joe@example.com");

        assertEquals("joe", user.getUsername());
        assertEquals("joe@example.com", user.getEmail());
    }
}
