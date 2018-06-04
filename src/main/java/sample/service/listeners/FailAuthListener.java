package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.model.User;

public interface FailAuthListener {
    @TransactionalEventListener
    void onSuccessFail(User user);
}
