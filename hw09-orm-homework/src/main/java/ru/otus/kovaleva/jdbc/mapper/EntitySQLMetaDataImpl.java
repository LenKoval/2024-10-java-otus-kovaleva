package ru.otus.kovaleva.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> metaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String getSelectAllSql() {
        var table = metaData.getName();
        if (table.isEmpty()) {
            throw new IllegalStateException("No table found in entity");
        }
        return "select * from " + table;
    }

    @Override
    public String getSelectByIdSql() {
        var idField = metaData.getIdField();
        if (idField == null) {
            throw new IllegalStateException("Id field not found");
        }
        return getSelectAllSql() + " where " + idField.getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        var fields = metaData.getFieldsWithoutId();
        if (fields.isEmpty()) {
            throw new IllegalStateException("No fields to insert");
        }

        var fieldsStr = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        var paramsStr = fields.stream()
                .map(f -> "?")
                .collect(Collectors.joining(", "));
        return String.format("insert into %s (%s) values (%s)",
                metaData.getName(),
                fieldsStr,
                paramsStr);
    }

    @Override
    public String getUpdateSql() {
        var fields = metaData.getFieldsWithoutId();
        if (fields.isEmpty()) {
            throw new IllegalStateException("No fields to update");
        }

        var setClause = fields.stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));
        var idField = metaData.getIdField();
        return String.format("update %s set %s where %s = ?",
                metaData.getName(),
                setClause,
                idField.getName());
    }
}
