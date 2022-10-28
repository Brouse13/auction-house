package es.brouse.auctionhouse.serialize;

import es.brouse.auctionhouse.exceptions.SerializationException;
import es.brouse.auctionhouse.serialize.serializers.MysqlSerializer;
import es.brouse.auctionhouse.serialize.serializers.Serializer;
import es.brouse.auctionhouse.serialize.serializers.YAMLSerializer;
import lombok.Getter;
import lombok.Setter;

public class EntitySerializer {
    @Getter @Setter private static String serializer = "yaml";

    public static <T extends Entity> Serializer<T> getSerializer(T entity) {
        if (!WrapperStorage.isWrapped(entity.getClass()))
            throw new SerializationException("Unable to wrap " + entity.getClass().getSimpleName());
        //TODO It needs to instance depending on the available serializer
        if ("yaml".equals(serializer))
            return new YAMLSerializer<>(entity);
        else
            return new MysqlSerializer<>(entity);
    }
}
