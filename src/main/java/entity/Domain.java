package entity;

import java.time.LocalDateTime;

/**
 * An abstract entity that tracks entity creation and updates.
 */
public abstract class Domain {
    private final String id;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Creates a domain entity.
     *
     * @param id The domain entity ID
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    protected Domain(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public String getId() { return id; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    /**
     * Records most recent update (touch) time.
     */
    protected void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
}
