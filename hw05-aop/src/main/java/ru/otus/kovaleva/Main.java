package ru.otus.kovaleva;

public class Main {

    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestLogging();
        testLogging.calculation(1);
        testLogging.calculation(1, 2);
        testLogging.calculation(1, 2, "3");
    }
}
