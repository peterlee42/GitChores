package entity;

import java.util.UUID;

/**
 * Utility class for generating unique domain entity IDs.
 */
public final class DomainIdGenerator {
    private static final int SHORT_UUID_LENGTH = 8;

    private DomainIdGenerator() {

    }

    /**
     * Generates a unique ID for a domain entity.
     *
     * @return a unique identifier string
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a unique ID with an entity-specific prefix for organization.
     *
     * @param prefix the prefix to add before the ID ("user", "room", "chore")
     * @return a unique identifier string with entity prefix
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    public static String generateIdWithPrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }
        return prefix + "-" + UUID.randomUUID();
    }

    /**
     * Generates a short UUID using the first 8 digits of a UUID.
     * Less collision resistant: unsuitable for critical IDs.
     *
     * @return a shortened 8-digit unique identifier string
     */
    public static String generateShortId() {
        return UUID.randomUUID().toString().substring(0, SHORT_UUID_LENGTH);
    }
}
