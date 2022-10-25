package es.brouse.auctionhouse.serialize;

import es.brouse.auctionhouse.exceptions.SerializationException;
import es.brouse.auctionhouse.serialize.serializers.Serializer;
import es.brouse.auctionhouse.serialize.serializers.YAMLSerializer;

public class EntitySerializer {

    public static <T extends Entity> Serializer<T> getSerializer(T entity) {
        if (!WrapperStorage.isWrapped(entity.getClass()))
            throw new SerializationException("Unable to wrap " + entity.getClass().getSimpleName());
        //TODO It needs to instance depending on the available serializer
        return new YAMLSerializer<T>(entity);
    }
}
