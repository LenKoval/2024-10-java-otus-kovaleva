package ru.otus.kovaleva.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kovaleva.core.repository.DataTemplate;
import ru.otus.kovaleva.core.repository.DataTemplateException;
import ru.otus.kovaleva.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    private static final Logger logger = LoggerFactory.getLogger(DataTemplateJdbc.class);

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(),
                List.of(id), rs -> {
                    try {
                        if (rs.next()) {
                            return getResult(rs);  // Возвращается результат, если запись найдена
                        }
                        return null;  // Возвращается null, если запись не найдена
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                });
    }

    private T getResult(ResultSet rs) {
        try {
            var object = entityClassMetaData.getConstructor().newInstance();
            for (var field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                field.set(object, rs.getObject(field.getName()));
            }
            return object;
        } catch (Exception e) {
            logger.error("Error in getResult(): ", e);
            throw new DataTemplateException(e);
        }
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(), rs -> {
                    var list = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            list.add(getResult(rs));
                        }
                        return list;
                    } catch (SQLException e) {
                        logger.error("Error in findAll(): ", e);
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(() -> {
            logger.error("Error in findAll().executeSelect");
            return new RuntimeException("Unexpected error");
        });
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection,
                    entitySQLMetaData.getInsertSql(),
                    getFieldsValues(client, entityClassMetaData.getFieldsWithoutId()));
        } catch (Exception e) {
            logger.error("Error in insert(): ", e);
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getFieldsValues(T client, List<Field> fields) {
        var list = new ArrayList<>();
        for (var field : fields) {
            field.setAccessible(true);
            try {
                list.add(field.get(client));
            } catch (IllegalAccessException e) {
                logger.error("Error getting field value: ", e);
                throw new DataTemplateException(e);
            }
        }
        return list;
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(connection,
                    entitySQLMetaData.getUpdateSql(),
                    getUpdateParams(client));
        } catch (Exception e) {
            logger.error("Error in update(): ", e);
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getUpdateParams(T client) {
        var params = getFieldsValues(client, entityClassMetaData.getFieldsWithoutId());
        params.add(getFieldValue(client, entityClassMetaData.getIdField()));
        return params;
    }

    private Object getFieldValue(T client, Field field) {
        field.setAccessible(true);
        try {
            return field.get(client);
        } catch (IllegalAccessException e) {
            logger.error("Error getting field value: ", e);
            throw new DataTemplateException(e);
        }
    }
}