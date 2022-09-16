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
        Arrays.stream(getFields()).forEach(fieldName -> section.set(fieldName, getValue.apply(fieldName)));
        storage.save();
        return storage.contains(Objects.requireNonNull(section.getCurrentPath()));
    }

    @Override
    public boolean saveEntity(String... fields) {
        Arrays.stream(fields).forEach(field -> section.set(field,
                Reflexion.getValue(entity, super.fields.get(field))));
        return true;
    }

    @Override
    public Object getEntity() {
        if (section == null) return null;
        return Reflexion.instance(entity.getClass(), Arrays.stream(getFields()).map(section::get).toArray());
    }

    @Override
    public List<Object> getEntities() {
        return Lists.newArrayList(getEntity());
    }

    @Override
    public boolean deleteEntity() {
        storage.set(Objects.requireNonNull(section.getCurrentPath()), null);
        return storage.contains(section.getCurrentPath());
    }
}
