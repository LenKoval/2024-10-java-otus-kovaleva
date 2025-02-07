package ru.otus.kovaleva.handler;

import ru.otus.kovaleva.listener.Listener;
import ru.otus.kovaleva.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);

    void removeListener(Listener listener);
}
