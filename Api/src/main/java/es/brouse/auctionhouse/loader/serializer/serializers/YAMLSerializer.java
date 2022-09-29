package es.brouse.auctionhouse.loader.serializer.serializers;

import com.google.common.collect.Lists;
import es.brouse.auctionhouse.loader.serializer.Reflexion;
import es.brouse.auctionhouse.loader.storage.YAML;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class YAMLSerializer extends Serializer {
    private final YAML storage = new YAML("storage/" + entityName, false);
    private final ConfigurationSection section;
    private final Function<String, Object> getValue = field -> Reflexion.getValue(entity, fields.get(field));

    public YAMLSerializer(Object entity) {
        super(entity);
        section = storage.createSection(entityName + "." + getValue.apply(identifier));
    }

    @Override
    public boolean createEntity() {
        if (!section.contains(Objects.requireNonNull(section.getCurrentPath()))) {
            Arrays.stream(getFields()).forEach(fieldName -> section.set(fieldName, getValue.apply(fieldName)));
            storage.reload();
            return true;
        }
        return false;
    }

    @Override
    public boolean saveEntity(String... fields) {
        Arrays.stream(fields).forEach(field -> section.set(field,
                Reflexion.getValue(entity, super.fields.get(field))));
        storage.reload();
        return true;
    }

    @Override
    public Object getEntity() {
        if (section == null) return null;
        Object[] args = Arrays.stream(getFields()).map(section::get).toArray();
        return Reflexion.instance(entity.getClass(),
                Arrays.stream(args).map(Object::getClass).toArray(Class[]::new), args);
    }

    @Override
    public Object[] getEntities(int from, int to) {
        ConfigurationSection section = storage.getConfigurationSection(entityName);
        List<Object> objects = Lists.newArrayList();

        if (section == null) return objects.toArray();

        //Get all the keys from the storage
        List<String> keys = Lists.newArrayList(section.getKeys(false));

        for (int i = from; i < keys.size(); i++) {
            //Lambda expressions must use final in to get from external lists
            int finalI = i;
            if (i > to) break;

            try {
                //Map all the fields from the entity on from the storage and instance it
                Object[] args = Arrays.stream(getFields())
                        .map(field -> section.get(keys.get(finalI) + "." + field)).toArray();
                objects.add(Reflexion.instance(entity.getClass(),
                        Arrays.stream(args).map(Object::getClass).toArray(Class[]::new), args));
            }catch (NullPointerException exception) {
                //If we found any NullPointerException we stop the for
                break;
            }
        }
        return objects.toArray();
    }

    @Override
    public boolean deleteEntity() {
        storage.set(Objects.requireNonNull(section.getCurrentPath()), null);
        return storage.contains(section.getCurrentPath());
    }
}
