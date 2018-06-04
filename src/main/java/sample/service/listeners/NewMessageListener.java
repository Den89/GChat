package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.model.Message;
import sample.model.User;
import sample.service.listeners.events.NewMessageEvent;

public interface NewMessageListener {
    @TransactionalEventListener
    void onNewMessage(NewMessageEvent event);
}
