package entity;

import java.util.UUID;

public final class DomainIdGenerator {
    private DomainIdGenerator() {

    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String generateIdWithPrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }
        return prefix + "-" + UUID.randomUUID().toString();
    }

    public static String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
