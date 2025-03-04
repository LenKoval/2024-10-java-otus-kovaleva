package ru.otus.kovaleva.jdbc.mapper;

import ru.otus.kovaleva.jdbc.mapper.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    private Constructor<T> constructor;

    private Field idField;

    private List<Field> allFields;

    private List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor == null) {
            try {
                constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("No-arg constructor not found for " + clazz.getName(), e);
            }
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        if (idField == null) {
            idField = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Id field not found for " + clazz.getName()));
        }
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        if (allFields == null) {
            allFields = Arrays.stream(clazz.getDeclaredFields())
                    .collect(Collectors.toList());
        }
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId == null) {
            fieldsWithoutId = getAllFields().stream()
                    .filter(field -> !field.equals(getIdField()))
                    .collect(Collectors.toList());
        }
        return fieldsWithoutId;
    }
}
