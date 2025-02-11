package ru.otus.kovaleva.listener.homework;

import ru.otus.kovaleva.listener.Listener;
import ru.otus.kovaleva.model.Message;
import ru.otus.kovaleva.model.ObjectForMessage;

import java.util.ArrayList;
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
            cloneMessage = new ObjectForMessage();
            List<String> originalData = msg.getField13().getData();
            if (originalData != null) {
                List<String> newData = new ArrayList<>(originalData);
                cloneMessage.setData(newData);
            }
        }
        return new Message.Builder(msg.getId())
                .field1(msg.getField1())
                .field2(msg.getField2())
                .field3(msg.getField3())
                .field4(msg.getField4())
                .field5(msg.getField5())
                .field6(msg.getField6())
                .field7(msg.getField7())
                .field8(msg.getField8())
                .field9(msg.getField9())
                .field10(msg.getField10())
                .field11(msg.getField11())
                .field12(msg.getField12())
                .field13(cloneMessage != null ? cloneMessage : msg.getField13())
                .build();
    }
}
