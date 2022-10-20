package es.brouse.auctionhouse.serialize.mapper;

import java.util.Map;

public class EntityReader {
    private final Map<String, Object> entity;

    public EntityReader(Map<String, Object> entity) {
        this.entity = entity;
    }

    public String getString(String key) {
        return String.valueOf(entity.get(key));
    }

    public Integer getInteger(String key) {
        return Integer.parseInt(String.valueOf(entity.get(key)));
    }

    public Long getLong(String key) {
        return Long.parseLong(String.valueOf(entity.get(key)));
    }

    public Float getFloat(String key) {
        return Float.parseFloat(String.valueOf(entity.get(key)));
    }

    public Double getDouble(String key) {
        return Double.parseDouble(String.valueOf(entity.get(key)));
    }

    public Short getShort(String key) {
        return Short.parseShort(String.valueOf(entity.get(key)));
    }

    public Byte getByte(String key) {
        return Byte.parseByte(String.valueOf(entity.get(key)));
    }
}
