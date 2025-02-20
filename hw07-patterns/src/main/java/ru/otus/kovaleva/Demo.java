package ru.otus.kovaleva;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.kovaleva.handler.ComplexProcessor;
import ru.otus.kovaleva.listener.ListenerPrinterConsole;
import ru.otus.kovaleva.model.Message;
import ru.otus.kovaleva.processor.LoggerProcessor;
import ru.otus.kovaleva.processor.ProcessorConcatFields;
import ru.otus.kovaleva.processor.ProcessorUpperField10;

import java.util.List;

public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        var processors = List.of(new ProcessorConcatFields(), new LoggerProcessor(new ProcessorUpperField10()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .build();

        var result = complexProcessor.handle(message);
        logger.info("result:{}", result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
