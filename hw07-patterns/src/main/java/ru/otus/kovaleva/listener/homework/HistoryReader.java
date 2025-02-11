package ru.otus.kovaleva.listener.homework;

import ru.otus.kovaleva.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
