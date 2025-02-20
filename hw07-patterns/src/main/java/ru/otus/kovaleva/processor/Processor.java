package ru.otus.kovaleva.processor;

import ru.otus.kovaleva.model.Message;

@SuppressWarnings("java:S1135")
public interface Processor {

    Message process(Message message);
}
