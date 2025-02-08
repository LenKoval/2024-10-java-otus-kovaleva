package ru.otus.kovaleva.listener.homework;

import ru.otus.kovaleva.listener.Listener;
import ru.otus.kovaleva.model.Message;

import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    
    @Override
    public void onUpdated(Message msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        throw new UnsupportedOperationException();
    }
}
