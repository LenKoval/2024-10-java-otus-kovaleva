package ru.otus.kovaleva;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kovaleva.annotations.After;
import ru.otus.kovaleva.annotations.Before;
import ru.otus.kovaleva.annotations.Test;

public class TestClass {

    private int counter = 0;

    private static final Logger logger = LoggerFactory.getLogger(TestClass.class);

    @Before
    public void setUp() {
        logger.info("Process Before");
        counter++;
    }

    @After
    public void tearDown() {
        logger.info("Process After");
        counter = 0;
    }

    @Test
    public void firstTest() {
        logger.info("Process firstTest()");
        assert counter == 1;
    }

    @Test
    public void secondTest() {
        logger.info("Process secondTest()");
        assert true;
    }

    @Test
    public void failedTest() {
        logger.info("Process failedTest()");
        throw new IllegalStateException();
    }
}
