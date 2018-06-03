package sample.service.subscribe;

import org.springframework.transaction.event.TransactionalEventListener;
import sample.model.Room;
import sample.model.User;

import java.util.Optional;

public interface SubscribeService {
    void subscribeUser(Room room, User user);
}
