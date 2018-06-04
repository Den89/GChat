package sample.service.subscribe;

import sample.model.Room;
import sample.model.Subscription;
import sample.model.User;

public interface SubscribeService {
    Subscription subscribeIfNotSubscribed(Room room, User user);
}
