package es.brouse.auctionhouse.loader.serializer.serializers;

import com.google.common.collect.Maps;
import es.brouse.auctionhouse.loader.serializer.Reflexion;
import es.brouse.auctionhouse.loader.serializer.Entity;
import es.brouse.auctionhouse.loader.serializer.Serializable;
import es.brouse.auctionhouse.loader.serializer.SerializationException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public abstract class Serializer {
    private final Class<?>[] TYPES = {Float.class, Integer.class, Short.class, Double.class, Byte.class, String.class};

    public final Object entity;
    public String entityName;
    public String identifier;
    public Map<String, Field> fields = Maps.newLinkedHashMap();

    public Serializer(Object entity) {
        this.entity = entity;
        loadName();
        loadFields();
    }

    /**
     * Create a new entity instance on the database
     * @return the operation status
     */
    public abstract boolean createEntity();

    /**
     * Save the given fields from the entity into the database
     * @param fields fields to save
     * @return the operation status
     */
    public abstract boolean saveEntity(String... fields);

    /**
     * Get the entity from the database with the given identifier.
     * @apiNote The entity only needs the {@link Serializable#identifier()} field
     * @return the entity with the given id
     */
    public abstract Object getEntity();

    /**
     * Get all the entities in a specific range from the database.
     * @apiNote This method won't access to any field
     * @return the entity with the given id
     */
    public abstract Object[] getEntities(int from, int to);

    /**
     * Delete the given entity from the database with the given identifier.
     * @apiNote The entity only needs the {@link Serializable#identifier()} field
     * @return the operation status
     */
    public abstract boolean deleteEntity();

    /**
     * Get all the fields that ca be serialized on the entity
     * @return the serialized fields
     */
    public String[] getFields() {
        return fields.keySet().toArray(new String[0]);
    }

    /**
     * Load the name form the entity using reflexion
     */
    private void loadName() {
        Entity annotation = Reflexion.getAnnotation(entity.getClass(), Entity.class);
        this.entityName = (!"".equals(annotation.name()) ?
                annotation.name().toLowerCase(Locale.ROOT) :
                entity.getClass().getSimpleName().toLowerCase(Locale.ROOT));
    }

    /**
     * Load the fields from the entity using reflexion
     */
    private void loadFields() {
        //Get all the fields annotated with Serializable
        for (Field field : Reflexion.getFields(entity.getClass(), Serializable.class)) {
            if (Arrays.stream(TYPES).noneMatch(aClass -> Reflexion.checkType(field.getType(), aClass))) continue;

            Serializable annotation = Reflexion.getAnnotation(field, Serializable.class);

            //Load identifier
            if (annotation.identifier()) {
                if (this.identifier != null)
                    throw new SerializationException("Class "+ this.entityName+ " has more than one identifier");
                else
                    this.identifier = field.getName();
            }
            this.fields.put(("".equals(annotation.name())) ? field.getName() : annotation.name(), field);
        }
    }
}
