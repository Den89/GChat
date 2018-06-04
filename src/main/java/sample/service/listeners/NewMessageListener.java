package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.model.Message;
import sample.model.User;

public interface NewMessageListener {
    @TransactionalEventListener
    void onNewMessage(Message message);
}
