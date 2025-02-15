package ru.otus.kovaleva;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ioc {

    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    public static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Object target;
        private static final Set<Method> annotatedMethods = new LinkedHashSet<>();

        DemoInvocationHandler(Object target) {
            Method[] declaredMethods = target.getClass().getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(Log.class)) {
                    annotatedMethods.add(method);
                }
            }
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method targetMethod = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

            if (annotatedMethods.contains(targetMethod)) {
                for (Object arg : args) {
                    logger.info("executed method: " + targetMethod.getName() + ", param:" + arg);
                }
            }
            return method.invoke(target, args);
        }
    }
}
