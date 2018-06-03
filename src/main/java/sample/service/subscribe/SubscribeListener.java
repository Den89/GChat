package sample.service.subscribe;

import org.springframework.transaction.event.TransactionalEventListener;

public interface SubscribeListener {
    @TransactionalEventListener
    void onSubscribe(SubscribeEvent event);
}
