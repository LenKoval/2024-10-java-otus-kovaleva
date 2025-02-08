package ru.otus.kovaleva.listener;

import ru.otus.kovaleva.model.Message;

@SuppressWarnings("java:S1135")
public interface Listener {

    void onUpdated(Message msg);
}
