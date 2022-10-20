package es.brouse.auctionhouse.loader.serialize.serializers;

import es.brouse.auctionhouse.loader.serialize.Entity;
import es.brouse.auctionhouse.loader.serialize.WrapperStorage;

import java.util.List;
import java.util.Map;

public abstract class Serializer {
    protected final Entity entity;
    protected final Map<String, Object> mapper;

    public Serializer(Entity entity) {
        this.entity = entity;
        this.mapper = WrapperStorage.getWrapper(entity).getMapper(entity).map();
    }

    public abstract boolean existsEntity();

    public abstract boolean saveEntity();

    public abstract <T extends Entity> T getEntity(T entity);
    public abstract <T extends Entity> List<T> getEntities(T entity, int from, int to);

    public abstract boolean deleteEntity();
}
