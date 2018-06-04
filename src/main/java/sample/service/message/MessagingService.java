package sample.service.message;

import sample.model.Message;
import sample.model.Room;
import sample.model.User;

import java.util.Map;
import java.util.Set;

public interface MessagingService {
    Map<Message, Set<User>> getReceivers();

    void report(User user, Room room, String text, boolean secret);
}
