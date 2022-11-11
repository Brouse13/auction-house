package es.brouse.auctionhouse.serialize;

import com.google.common.collect.Maps;
import es.brouse.auctionhouse.entities.AuctionHouseItem;
import es.brouse.auctionhouse.entities.Balance;
import es.brouse.auctionhouse.exceptions.SerializationException;

import java.util.Map;

public class WrapperStorage {
    private static final Map<Class<?>, EntityWrapper<?>> WRAPPERS = Maps.newHashMap();

    public static boolean isWrapped(Class<?> entity) {
        return WRAPPERS.containsKey(entity);
    }

    public static <T extends Entity> void addWrapper(Class<T> entity, EntityWrapper<T> entityWrapper) {
        WRAPPERS.put(entity, entityWrapper);
    }

    public static void removeWrapper(Class<?> entity) {
        WRAPPERS.remove(entity);
    }

    public static <T extends Entity> EntityWrapper<T> getWrapper(T entity) {
        if (isWrapped(entity.getClass()))
            return (EntityWrapper<T>) WRAPPERS.get(entity.getClass());
        throw new SerializationException("No wrapper for class '" + entity.getClass().getSimpleName() + "'");
    }

    static {
        addWrapper(AuctionHouseItem.class, new AuctionHouseItem.Wrapper());
        addWrapper(Balance.class, new Balance.Wrapper());
    }
}
