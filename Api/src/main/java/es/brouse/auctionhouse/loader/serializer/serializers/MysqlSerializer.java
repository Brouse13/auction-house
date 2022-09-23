package es.brouse.auctionhouse.loader.serializer.serializers;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import es.brouse.auctionhouse.loader.serializer.Reflexion;
import es.brouse.auctionhouse.loader.serializer.SerializationException;
import es.brouse.auctionhouse.loader.storage.Mysql;
import es.brouse.auctionhouse.loader.utils.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MysqlSerializer extends Serializer {
    private final HashMap<Class<?>, String> equivalence = new HashMap<>();

    public MysqlSerializer(Object entity) {
        super(entity);

        //Add the equivalences of fields to sql fields
        equivalence.put(Float.class, "float");
        equivalence.put(Integer.class, "int");
        equivalence.put(Double.class, "double");
        equivalence.put(Short.class, "smallint");
        equivalence.put(Byte.class, "tinyint");
        equivalence.put(String.class, "varchar(255)");
        createTable();
    }

    @Override
    public boolean createEntity() {
        //Create sql query
        String query = String.format("INSERT INTO %s (%s) VALUES(%s);",
                entityName, String.join(",", fields.keySet()),
                fields.values().stream()
                        .map(this::parseField)
                        .collect(Collectors.joining(", ")));

        try(Statement statement = Mysql.getConnection().createStatement()) {
            return statement.executeUpdate(query) == 1;
        } catch (SQLException exception) {
            throw new SerializationException(exception);
        }
    }

    @Override
    public boolean saveEntity(String... saveFields) {
        //Validate if entity fields can be serialized
        Map<String, Field> validFields = validateFields(saveFields);
        if (validFields.isEmpty())
            throw new SerializationException("Unable to serialize "+ String.join(", ", saveFields));

        //Create sql query
        String query = String.format("UPDATE %s SET %s WHERE %s=%s;",
                entityName, validFields.entrySet().stream()
                        .map(entry -> entry.getKey() + "=" + parseField(entry.getValue()))
                        .collect(Collectors.joining(", ")),
                identifier,
                parseField(fields.get(identifier)));

        try(Statement statement = Mysql.getConnection().createStatement()) {
            return statement.executeUpdate(query) == 1;
        } catch (SQLException exception) {
            throw new SerializationException(exception);
        }
    }

    @Override
    public Object getEntity() {
        //Create sql query
        String query = String.format("SELECT *  FROM %s WHERE %s=%s;",
                entityName, identifier, parseField(fields.get(identifier)));

        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    //Create a new instance
                    Set<Object> values = Sets.newLinkedHashSet();
                    for (Map.Entry<String, Field> entry : fields.entrySet()) {
                        values.add(resultSet.getObject(entry.getKey(), entry.getValue().getType()));
                    }

                    return Reflexion.instance(super.entity.getClass(),
                            Arrays.stream(values.toArray()).map(Object::getClass).toArray(Class[]::new),
                            values.toArray());
                }
                return null;
            }
        }catch (SQLException exception) {
            throw new SerializationException(exception);
        }
    }

    @Override
    public Object[] getEntities(int from, int to) {
        List<Object> objects = Lists.newArrayList();
        String query = String.format("SELECT * FROM %s LIMIT %d,%d",
                entityName, from - 1, to - 1);

        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    //Create a new instance
                    Set<Object> values = Sets.newLinkedHashSet();
                    for (Map.Entry<String, Field> entry : fields.entrySet()) {
                        values.add(resultSet.getObject(entry.getKey(), entry.getValue().getType()));
                    }

                    objects.add(Reflexion.instance(super.entity.getClass(),
                            Arrays.stream(values.toArray()).map(Object::getClass).toArray(Class[]::new),
                            values.toArray()));
                }
                return objects.toArray();
            }
        }catch (SQLException exception) {
            throw new SerializationException(exception);
        }
    }

    @Override
    public boolean deleteEntity() {
        String query = String.format("DELETE FROM %s WHERE %s=%s;",
                entityName, identifier,
                parseField(fields.get(identifier)));

        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            return statement.executeUpdate() == 1;
        } catch (SQLException exception) {
            throw new SerializationException(exception);
        }
    }

    private String parseField(Field field) {
        Object value = Reflexion.getValue(super.entity, field);

        if (Reflexion.checkType(field.getType(), String.class)) {
            return "'" + value + "'";
        }else {
            return value.toString();
        }
    }

    private Map<String, Field> validateFields(String... fields) {
        return Arrays.stream(fields)
                .filter(field -> super.fields.containsKey(field))
                .collect(Collectors.toMap(Function.identity(), o -> super.fields.get(o)));
    }

    private void createTable() {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s);",
                entityName, fields.keySet().stream()
                        .map(s -> s + " " + equivalence.get(fields.get(s).getType()))
                        .collect(Collectors.joining(", ")));
        try(Connection connection = Mysql.getConnection()) {
            connection.prepareStatement(sql).executeUpdate();
        }catch (SQLException exception) {
            Logger.error(exception.getMessage());
        }
    }
}
