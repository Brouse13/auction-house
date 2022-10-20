package es.brouse.auctionhouse.serialize;

import es.brouse.auctionhouse.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.serialize.mapper.EntityReader;

public interface EntityWrapper<T extends Entity> {
    /**
     * Retrieve the entity from the {@link EntityReader}
     * @param entityReader reader of the entity
     * @return the instanced entity
     */
    T mapEntity(EntityReader entityReader);

    /**
     * Get the mapper that will get all the fields
     * to serialize
     * @param entity entity to serialize
     * @return the mapped entity
     */
    EntityMapper<T> getMapper(T entity);
}
