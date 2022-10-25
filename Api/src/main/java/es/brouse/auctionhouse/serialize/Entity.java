package es.brouse.auctionhouse.serialize;

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
     * @return the unique entity identifier
     */
    String getIdentifier();
}
