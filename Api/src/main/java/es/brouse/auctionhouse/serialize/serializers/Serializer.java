package es.brouse.auctionhouse.serialize.serializers;

import es.brouse.auctionhouse.serialize.Entity;
import es.brouse.auctionhouse.serialize.WrapperStorage;

import java.util.List;
import java.util.Map;

public abstract class Serializer<T extends Entity> {
    protected final T entity;
    protected final Map<String, Object> mapper;

    public Serializer(T entity) {
        this.entity = entity;
        this.mapper = WrapperStorage.getWrapper(entity).getMapper(entity).map();
    }

    public abstract boolean existsEntity();

    public abstract boolean saveEntity();

    public abstract T getEntity();
    public abstract List<T> getEntities(int from, int to);

    public abstract boolean deleteEntity();
}
