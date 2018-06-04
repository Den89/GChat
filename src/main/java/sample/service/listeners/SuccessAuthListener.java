package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.service.listeners.events.SuccessAuthEvent;

public interface SuccessAuthListener {
    @TransactionalEventListener
    void onSuccessAuth(SuccessAuthEvent event);
}
