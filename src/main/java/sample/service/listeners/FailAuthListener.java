package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.service.listeners.events.FailAuthEvent;

public interface FailAuthListener {
    @TransactionalEventListener
    void onSuccessFail(FailAuthEvent event);
}
