package es.brouse.auctionhouse.serialize.serializers;

import com.google.common.collect.Lists;
import es.brouse.auctionhouse.serialize.Entity;
import es.brouse.auctionhouse.serialize.EntityWrapper;
import es.brouse.auctionhouse.serialize.WrapperStorage;
import es.brouse.auctionhouse.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.serialize.mapper.EntityReader;
import es.brouse.auctionhouse.storage.Mysql;
import es.brouse.auctionhouse.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MysqlSerializer<T extends Entity> extends Serializer<T> {
    private static final String EXIST_ENTITY = "SELECT * FROM %s WHERE _id=%s";
    private static final String GET_ENTITY = "SELECT * FROM %s LIMIT WHERE _id=%s";
    private static final String GET_ENTITIES = "SELECT * FROM %s LIMIT %d, %d";
    private static final String SAVE_ENTITY = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String DELETE_ENTITY = "DELETE FROM %s WHERE _id=%s";


    public MysqlSerializer(T entity) {
        super(entity);

        if (!WrapperStorage.isWrapped(entity.getClass()))
            throw new NullPointerException("Cannot wrap class " + entity.getName());
    }

    @Override
    public boolean existsEntity() {
        //REPLACE: (entity name, entity identifier)
        String query = String.format(EXIST_ENTITY, entity.getName(), entity.getIdentifier());

        //Create a prepared statement to check if the database contains the key
        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException exception) {
            Logger.error("Error while getting entity");
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean saveEntity() {
        EntityMapper<T> mapper = WrapperStorage.getWrapper(entity).getMapper(entity);

        //REPLACE: (entity name, identifier of entity, values of entity)
        String query = String.format(SAVE_ENTITY, entity.getName(),
                String.join(",", mapper.map().keySet()),
                String.join(",", mapper.map().values().stream()
                        .map(Object::toString).collect(Collectors.joining(","))));

        //Call the executeUpdate method
        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            return statement.executeUpdate() == 1;
        }catch (SQLException exception) {
            Logger.error("Error while getting entity");
            throw new RuntimeException(exception);
        }
    }

    @Override
    public T getEntity() {
        EntityWrapper<T> wrapper = WrapperStorage.getWrapper(entity);

        //REPLACE: (entity name, identifier of entity)
        String query = String.format(GET_ENTITY, entity.getName(), entity.getIdentifier());

        //Call the executeUpdate method
        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                EntityMapper<T> entityMapper = new EntityMapper<>(entity);

                for (String entry : wrapper.getMapper(entity).map().keySet()) {
                    entityMapper.set(entry, resultSet.getObject(entry));
                }
                return wrapper.mapEntity(new EntityReader(entityMapper.map()));
            }

        }catch (SQLException exception) {
            Logger.error("Error while getting entity");
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<T> getEntities(int from, int to) {
        List<T> entities = Lists.newArrayList();
        if (!WrapperStorage.isWrapped(entity.getClass())) return entities;

        //REPLACE: (entity name, from, to)
        String query = String.format(GET_ENTITIES, entity.getName(), from, to);
        EntityWrapper<T> wrapper = WrapperStorage.getWrapper(entity);

        //Create a statement to get all the entities in range [from, to]
        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {

                //Loop throw all the fround entities in range [from, to]
                while (resultSet.next()) {
                    EntityMapper<T> entityMapper = new EntityMapper<>(entity);

                    /* Due to we can't get all the fields from the ResultSet, we get the keys from a
                    reverse mapping */
                    for (String entry : wrapper.getMapper(entity).map().keySet()) {
                        entityMapper.set(entry, resultSet.getObject(entry));
                    }
                    entities.add(wrapper.mapEntity(new EntityReader(entityMapper.map())));
                }
            }
        }catch (SQLException exception) {
            Logger.error("Error while getting entities");
            throw new RuntimeException(exception);
        }
        return entities;
    }

    @Override
    public boolean deleteEntity() {
        //REPLACE: (entity name, entity identifier)
        String query = String.format(DELETE_ENTITY, entity.getName(), entity.getIdentifier());

        //Create a prepared statement to check if the database contains the key
        try(PreparedStatement statement = Mysql.getConnection().prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException exception) {
            Logger.error("Error while getting entity");
            throw new RuntimeException(exception);
        }
    }
}
