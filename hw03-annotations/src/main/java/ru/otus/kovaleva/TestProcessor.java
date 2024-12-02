package ru.otus.kovaleva;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kovaleva.annotations.After;
import ru.otus.kovaleva.annotations.Before;
import ru.otus.kovaleva.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TestProcessor.class);

    public static void process(String className)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {

        Class<?> testClass = Class.forName(className);
        List<Method> beforeMethods = getAnnotatedMethods(testClass, Before.class);
        List<Method> testMethods = getAnnotatedMethods(testClass, Test.class);
        List<Method> afterMethods = getAnnotatedMethods(testClass, After.class);

        int sucessfullCount = 0;
        int failedCount = 0;

        for (Method testMethod : testMethods) {
            var testClassInstance = testClass.getConstructor().newInstance();
            try {
                for (Method method : beforeMethods) {
                    invokeMethod(method, testClassInstance);
                }
                testMethod.invoke(testClassInstance);
                logger.info("Test: " + testMethod.getName() + " passed");
                sucessfullCount++;
            } catch (InvocationTargetException e) {
                logger.info("Test: %s fail: %s".formatted(testMethod.getName(), e.getTargetException().getMessage()));
                failedCount++;
            }
            finally {
                for (Method method : afterMethods) {
                    invokeMethod(method, testClassInstance);
                }
            }
        }
        getStatistic(sucessfullCount, failedCount);
    }

    private static void getStatistic(int sucessfullCount, int failedCount) {
        logger.info("Test results:");
        logger.info("Executed tests: {}", sucessfullCount + failedCount);
        logger.info("Failed tests: {}", failedCount);
        logger.info("Passed tests: {}", sucessfullCount);
    }

    private static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(a -> a.isAnnotationPresent(annotation))
                .toList();
    }

    private static <T> void invokeMethod(Method method, T testClassInstance) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(testClassInstance);
    }
}
