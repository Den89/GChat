package sample.service.listeners.events;

import sample.model.Message;

public class NewMessageEvent {
    private Message message;

    public NewMessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
