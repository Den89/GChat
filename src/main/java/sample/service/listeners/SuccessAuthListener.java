package sample.service.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.model.User;

public interface SuccessAuthListener {
    @TransactionalEventListener
    void onSuccessAuth(User user);
}
