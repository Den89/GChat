package sample.service.message;

import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.socket.WebSocketSession;
import sample.model.Message;
import sample.model.Room;
import sample.model.User;
import sample.service.subscribe.SubscribeEvent;

import java.util.Map;
import java.util.Set;

public interface MessagingService {
    Map<Message, Set<User>> getReceivers();

    void report(User user, Room room, String text, boolean secret);

    void addMessageInRoom(Room room, Message message);

    void sendMessageToSubscribers(Message message);
}
