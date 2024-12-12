package ru.otus.kovaleva;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kovaleva.exceptions.TestException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws TestException {

        TestResult result = TestProcessor.process(TestClass.class.getName());
        logger.info("Test results:");
        logger.info("Executed tests: {}", result.getSuccessfulTests() + result.getFailedTests());
        logger.info("Failed tests: {}", result.getFailedTests());
        logger.info("Passed tests: {}", result.getSuccessfulTests());
    }
}
