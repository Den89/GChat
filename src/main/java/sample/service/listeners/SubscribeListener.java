package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.service.listeners.events.NewSubscriptionEvent;

public interface SubscribeListener {
    @TransactionalEventListener
    void onSubscribe(NewSubscriptionEvent event);
}
