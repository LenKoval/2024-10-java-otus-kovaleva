package ru.otus.kovaleva;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {

        TestProcessor.process(TestClass.class.getName());
    }
}
