package es.brouse.auctionhouse.serialize;

import java.util.UUID;

public interface Entity {
    /**
     * Return the default name of the class that matches
     * to the class name in lowercase
     * @return the default name
     */
    default String getName() {
        return getClass().getSimpleName().toLowerCase();
    }

    /**
     * Get the default identifier of the entity. It must be unique
     * If not implemented, it will use a random UUID
     * @return the unique entity identifier
     */
    default String getIdentifier() {
        return UUID.randomUUID().toString();
    }
}
