package sample.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.*;
import sample.repository.MessageRepository;
import sample.service.listeners.events.NewMessageEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.messageRepository = messageRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message saveAndPublish(User user, Room room, boolean secret, String text) {
        return messageRepository.save(createMessage(user, room, secret, text));
    }


    private Message createMessage(User user, Room room, boolean secret, String text) {
        Message message = new Message();
        message.setUser(user);
        message.setRoom(room);
        message.setTime(LocalDateTime.now());
        message.setSecret(secret);
        message.setText(text);

        Set<MessageReceiveHistory> historyList = room.getSubscriptions()
                .stream()
                .map(Subscription::getUser)
                .filter(u -> !message.isSecret() || (message.isSecret() && u.getRank() >= message.getUser().getRank()))
                .map(u -> {
                    MessageReceiveHistory history = new MessageReceiveHistory();
                    history.setMessage(message);
                    history.setReceiver(u);
                    return history;
                }).collect(Collectors.toSet());

        message.getReceiveHistory().addAll(historyList);

        applicationEventPublisher.publishEvent(new NewMessageEvent(message));
        return message;
    }
}
