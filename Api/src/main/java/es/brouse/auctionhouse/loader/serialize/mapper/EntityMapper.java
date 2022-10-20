package es.brouse.auctionhouse.loader.serialize.mapper;

import es.brouse.auctionhouse.loader.exceptions.SerializationException;
import es.brouse.auctionhouse.loader.serialize.Entity;

import java.util.HashMap;
import java.util.Map;

public class EntityMapper<T extends Entity> {
    private final T object;
    private final Map<String, Object> entity = new HashMap<>();

    public EntityMapper(T object) {
        this.object = object;
    }

    public EntityMapper<T> set(String key, String value) {
        entity.put(key, value);
        return this;
    }

    public EntityMapper<T> set(String key, Byte value) {
        entity.put(key, value);
        return this;
    }

    public EntityMapper<T> set(String key, Double value) {
        entity.put(key, value);
        return this;
    }

    public EntityMapper<T> set(String key, Short value) {
        entity.put(key, value);
        return this;
    }

    public EntityMapper<T> set(String key, Integer value) {
        entity.put(key, value);
        return this;
    }

    public EntityMapper<T> set(String key, Long value) {
        entity.put(key, value);
        return this;
    }

    public EntityMapper<T> set(String key, Float value) {
        entity.put(key, value);
        return this;
    }

    public EntityMapper<T> set(String key, Object value) {
        String str = String.valueOf(value);
        Class<?> type = value.getClass();

        if (type.isAssignableFrom(String.class)) {
            return set(key, str);
        }else if (type.isAssignableFrom(Byte.class)) {
            return set(key, Byte.parseByte(str));
        }else if (type.isAssignableFrom(Short.class)) {
            return set(key, Short.parseShort(str));
        }else if (type.isAssignableFrom(Double.class)) {
            return set(key, Double.parseDouble(str));
        }else if (type.isAssignableFrom(Integer.class)) {
            return set(key, Integer.parseInt(str));
        }else if (type.isAssignableFrom(Float.class)) {
            return set(key, Float.parseFloat(str));
        }else if (type.isAssignableFrom(Long.class)) {
            return set(key, Long.parseLong(str));
        }
        throw new SerializationException("Class is not '" + type.getSimpleName() + "' serializable");
    }

    public Map<String, Object> map() {
        set("_id", object.getIdentifier());
        return entity;
    }
}