package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class UserFactory {

    /**
     * User factory object.
     * 
     * @param id       the ID of the user
     * @param username the username of the user
     * @param email    the email of the user
     * @return a new User object
     */
    public User create(String id, String username, String email) {
        return new User(id, username, email);
    }
}
