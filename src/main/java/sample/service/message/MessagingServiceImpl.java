package sample.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Message;
import sample.model.Room;
import sample.model.User;
import sample.service.listeners.events.NewMessageEvent;
import sample.service.ws.WsSessionSender;
import sample.service.subscribe.SubscribeService;

import java.util.*;

@Service
@Transactional
public class MessagingServiceImpl implements MessagingService {
    @Autowired
    private WsSessionSender wsSessionSender;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private final Map<Message, Set<User>> receiversByMessage = new HashMap<>();

    @Override
    public Map<Message, Set<User>> getReceivers() {
        return receiversByMessage;
    }

    @Override
    public void report(User user, Room room, String text, boolean secret) {
        subscribeService.subscribeUser(room, user);
        Message msg = messageService.saveAndFlush(user, room, secret, text);
        addMessageInRoom(room, msg);
        applicationEventPublisher.publishEvent(new NewMessageEvent(msg));
    }

    @Override
    public void addMessageInRoom(Room room, Message message) {
        room.getMessages().add(message);
    }

}
