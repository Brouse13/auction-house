package es.brouse.auctionhouse.loader.serializer;

import com.google.common.collect.Maps;
import es.brouse.auctionhouse.loader.serializer.serializers.Serializer;
import lombok.NonNull;

import java.util.Collections;
import java.util.Map;

public final class SerializerManager {
        private static final Map<String, Class<? extends Serializer>> serializers = Maps.newHashMap();
        private static String active = "yaml";


    /**
     * Add a new Serializer to the usable serializers
     * @param name serializer name
     * @param serializer serializer to add
     * @return if the serializer was added
     */
    public static boolean addSerializer(String name, Class<? extends Serializer> serializer) {
        return serializers.putIfAbsent(name, serializer) == null;
    }

    public static boolean setActive(@NonNull String name) {
        if (!serializers.containsKey(name)) return false;
        active = name;
        return true;
    }

    /**
     * Get the serializer that will manage the serialization
     * of the given entity.
     * @param entity entity to serialize
     * @return the found serializer
     */
    private static Serializer getSerializer(Object entity) {
        return Reflexion.instance(serializers.get(active), Collections.singletonList(entity).toArray());
    }
}
