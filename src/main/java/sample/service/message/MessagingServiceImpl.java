package sample.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Message;
import sample.model.MessageReceiveHistory;
import sample.model.Room;
import sample.model.User;
import sample.service.listeners.events.NewMessageEvent;
import sample.service.subscribe.SubscribeService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class MessagingServiceImpl implements MessagingService {
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Map<Message, Set<User>> getReceivers() {
        return messageService.findAll()
                .stream()
                .collect(Collectors.toMap(m -> m, m -> m.getReceiveHistory()
                        .stream()
                        .map(MessageReceiveHistory::getReceiver).collect(Collectors.toSet())));
    }

    @Override
    public void report(User user, Room room, String text, boolean secret) {
        subscribeService.subscribeIfNotSubscribed(room, user);
        applicationEventPublisher.publishEvent(new NewMessageEvent(messageService.save(user, room, secret, text)));
    }
}
