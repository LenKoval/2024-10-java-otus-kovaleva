package ru.otus.kovaleva.processor;

import ru.otus.kovaleva.processor.service.TimeBasedService;
import ru.otus.kovaleva.model.Message;
import ru.otus.kovaleva.processor.exceptions.TimeBasedException;

public class TimeBasedExceptionProcessor implements Processor {

    private final TimeBasedService timeBasedService;

    public TimeBasedExceptionProcessor(TimeBasedService timeBasedService) {
        this.timeBasedService = timeBasedService;
    }

    @Override
    public Message process(Message message) {
        if (timeBasedService.getTime().getSecond() % 2 == 0) {
            throw new TimeBasedException("Exception thrown in even second");
        }
        return message;
    }
}
