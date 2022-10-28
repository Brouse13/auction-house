package es.brouse.auctionhouse.serialize.serializers;

import es.brouse.auctionhouse.serialize.Entity;
import es.brouse.auctionhouse.serialize.EntityWrapper;
import es.brouse.auctionhouse.serialize.WrapperStorage;
import es.brouse.auctionhouse.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.serialize.mapper.EntityReader;
import es.brouse.auctionhouse.storage.YAML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YAMLSerializer<T extends Entity> extends Serializer<T> {
    private final YAML yaml;
    private final String key = entity.getName() + "." + entity.getIdentifier();


    public YAMLSerializer(T entity) {
        super(entity);
        yaml = new YAML("storage/" + entity.getName(), false);
    }

    @Override
    public boolean existsEntity() {
        //Check if the identifier is present on the storage, os always a String
        return yaml.contains(entity.getName() + "." + mapper.get("_id"));
    }

    @Override
    public boolean saveEntity() {
        mapper.forEach((s, o) -> yaml.set(key + "." + s, o));
        yaml.reload();
        return true;
    }

    @Override
    public T getEntity() {
        if (!existsEntity()) return null;

        EntityWrapper<T> wrapper = WrapperStorage.getWrapper(entity);
        EntityMapper<T> entityMapper = new EntityMapper<>(entity).set("_id", entity.getIdentifier());

        yaml.getConfigurationSection(key).getKeys(false).forEach(s -> entityMapper.set(s, yaml.get(key+ "." + s)));

        return wrapper.mapEntity(new EntityReader(entityMapper.map()));
    }

    @Override
    public List<T> getEntities(int from, int to) {
        if (!WrapperStorage.isWrapped(entity.getClass())) return null;

        //Initialization of return list and Entity wrapper
        List<T> entities = new ArrayList<>();
        EntityWrapper<T> wrapper = WrapperStorage.getWrapper(entity);

        //Loop only throw the entities queried we call Math#min() to avoid NullPointerException
        String[] entityKeys = yaml.getConfigurationSection(entity.getName()).getKeys(false).toArray(new String[0]);
        entityKeys = Arrays.copyOfRange(entityKeys, from, Math.min(to, entityKeys.length));

        for (String entityId : entityKeys) {
            //Create mapper to store entity values with the initial _id
            EntityMapper<T> entityMapper = new EntityMapper<>(entity).set("_id", entityId);

            //Add all the keys of the section into the mapper
            yaml.getConfigurationSection(entity.getName() + "." + entityId).getKeys(false).forEach(s ->
                entityMapper.set(s, yaml.get(entity.getName() + "." + entityId + "." + s)));

            //Add the entity from the wrapper
            entities.add(wrapper.mapEntity(new EntityReader(entityMapper.map())));
        }

        return entities;
    }

    @Override
    public boolean deleteEntity() {
        yaml.set(key, null);
        yaml.reload();
        return true;
    }

}
