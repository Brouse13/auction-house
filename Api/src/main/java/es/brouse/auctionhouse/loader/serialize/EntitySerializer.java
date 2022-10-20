package es.brouse.auctionhouse.loader.serialize;

import es.brouse.auctionhouse.loader.exceptions.SerializationException;
import es.brouse.auctionhouse.loader.serialize.serializers.Serializer;
import es.brouse.auctionhouse.loader.serialize.serializers.YAMLSerializer;

public class EntitySerializer {

    public static Serializer getSerializer(Entity entity) {
        if (!WrapperStorage.isWrapped(entity.getClass()))
            throw new SerializationException("Unable to wrap " + entity.getClass().getSimpleName());
        //TODO It needs to instance depending on the available serializer
        return new YAMLSerializer(entity);
    }
}
