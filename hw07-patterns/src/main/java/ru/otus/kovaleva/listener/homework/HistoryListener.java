package ru.otus.kovaleva.listener.homework;

import ru.otus.kovaleva.listener.Listener;
import ru.otus.kovaleva.model.Message;
import ru.otus.kovaleva.model.ObjectForMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final HashMap<Long, Message> messageHistory = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        messageHistory.put(msg.getId(), cloneMessage(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageHistory.get(id)).map(this::cloneMessage);
    }

    private Message cloneMessage(Message msg) {
        ObjectForMessage cloneMessage = null;
        if (msg.getField13() != null) {
            cloneMessage = msg.getField13();
            cloneMessage.setData(List.copyOf(msg.getField13().getData()));
        }
        return null; // ?????????????????
    }
}
