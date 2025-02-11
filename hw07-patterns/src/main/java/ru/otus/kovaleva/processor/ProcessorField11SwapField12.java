package ru.otus.kovaleva.processor;

import ru.otus.kovaleva.model.Message;

public class ProcessorField11SwapField12 implements Processor {

    @Override
    public Message process(Message message) {
        return message.toBuilder()
                .field11(message.getField12())
                .field12(message.getField11())
                .build();
    }
}
