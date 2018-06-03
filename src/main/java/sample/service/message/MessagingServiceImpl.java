package sample.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.model.Message;
import sample.model.Room;
import sample.model.User;
import sample.service.session.WsSessionManager;
import sample.service.session.WsSessionSender;
import sample.service.subscribe.SubscribeEvent;
import sample.service.subscribe.SubscribeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessagingServiceImpl implements MessagingService {
    @Autowired
    private WsSessionSender wsSessionSender;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private MessageService messageService;

    private final Map<Room, Set<User>> usersByRoom = new HashMap<>();
    private final Map<Room, Set<Message>> messagesByRoom = new HashMap<>();
    private final Map<Message, Set<User>> receiversByMessage = new HashMap<>();


    @Override
    public Map<Message, Set<User>> getReceivers() {
        return receiversByMessage;
    }

    @Override
    public void report(User user, Room room, String text, boolean secret) {
        synchronized (messagesByRoom) {
            subscribeService.subscribeUser(room, user);

            Message msg = messageService.create(user, room, secret, text);
            addMessageInRoom(room, msg);
            sendMessageToSubscribers(msg);
        }
    }


    @Override
    public void addMessageInRoom(Room room, Message message) {

        Set<Message> messagesInRoom = room.getMessages();
        if (messagesInRoom == null) {
            messagesInRoom = new HashSet<>();
            messagesByRoom.put(room, messagesInRoom);
        }
        messagesInRoom.add(message);

        Set<User> receivers;
        if (message.isSecret()) {
            receivers = usersByRoom.get(room).stream()
                    .filter(user -> user.getRank() >= message.getUser().getRank()).collect(Collectors.toSet());
        } else {
            receivers = new HashSet<>(usersByRoom.get(room));
        }
        receiversByMessage.put(message, receivers);

    }

    @Override
    public void sendMessageToSubscribers(Message message) {
        message.getRoom().getUsers().forEach(user -> {
            if (message.isSecret() && user.getRank() < message.getUser().getRank()) {
                return;
            }
            wsSessionSender.sendToUser(user, message.toJSON().toJSONString());
        });
    }
}
