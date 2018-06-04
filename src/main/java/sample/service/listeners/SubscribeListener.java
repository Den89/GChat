package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.service.listeners.events.SubscribeEvent;

public interface SubscribeListener {
    @TransactionalEventListener
    void onSubscribe(SubscribeEvent event);
}
