package es.brouse.auctionhouse.serialize.serializers;

import es.brouse.auctionhouse.serialize.Entity;
import es.brouse.auctionhouse.serialize.EntityWrapper;
import es.brouse.auctionhouse.serialize.WrapperStorage;
import es.brouse.auctionhouse.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.serialize.mapper.EntityReader;
import es.brouse.auctionhouse.storage.YAML;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YAMLSerializer extends Serializer {
    private final YAML yaml;
    private final ConfigurationSection storageSection;

    public YAMLSerializer(Entity entity) {
        super(entity);
        yaml = new YAML("storage/" + entity.getName(), false);
        storageSection = yaml.createSection(entity.getName());
    }

    @Override
    public boolean existsEntity() {
        //Check if the identifier is present on the storage, os always a String
        System.out.println(storageSection);
        return storageSection.contains(String.valueOf(mapper.get("_id")));
    }

    @Override
    public boolean saveEntity() {
        mapper.forEach((s, o) -> storageSection.set(entity.getIdentifier() + "." + s, o));
        yaml.reload();
        return true;
    }

    @Override
    public <T extends Entity> T getEntity(T entity) {
        if (!existsEntity()) return null;

        EntityMapper<T> entityMapper = new EntityMapper<T>(entity);

        //Add all the keys of the section into the mapper from the identifier
        storageSection.getConfigurationSection(entity.getIdentifier()).getKeys(false).forEach(s -> {
            entityMapper.set(entity.getIdentifier(), storageSection.get(entity.getIdentifier() + "." + s));
        });

        //Return the entity mapped
        return WrapperStorage.getWrapper(entity).mapEntity(new EntityReader(entityMapper.map()));
    }

    @Override
    public <T extends Entity> List<T> getEntities(T entity, int from, int to) {
        if (!existsEntity() || !WrapperStorage.isWrapped(entity.getClass())) return null;

        //Loop only throw the entities queried
        String[] keys = storageSection.getKeys(false).toArray(new String[0]);
        keys = Arrays.copyOfRange(keys, from, to);

        List<T> entities = new ArrayList<>();
        EntityWrapper<T> wrapper = WrapperStorage.getWrapper(entity);

        for (String key : keys) {
            EntityMapper<T> entityMapper = new EntityMapper<>(entity);

            //Add all the keys of the section into the mapper
            storageSection.getConfigurationSection(key).getKeys(false).forEach(s -> {
                entityMapper.set(key, storageSection.get(key + "." + s));
            });

            //Add the entity from the wrapper
            entities.add(wrapper.mapEntity(new EntityReader(entityMapper.map())));
        }

        return entities;
    }

    @Override
    public boolean deleteEntity() {
        storageSection.set(entity.getIdentifier(), null);
        yaml.reload();
        return true;
    }

}
