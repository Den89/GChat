package sample.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Message;
import sample.model.MessageReceiveHistory;
import sample.model.Room;
import sample.model.User;
import sample.service.subscribe.SubscribeService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessagingServiceImpl implements MessagingService {
    private final SubscribeService subscribeService;
    private final MessageService messageService;

    @Autowired
    public MessagingServiceImpl(SubscribeService subscribeService, MessageService messageService) {
        this.subscribeService = subscribeService;
        this.messageService = messageService;
    }

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
        messageService.saveAndPublish(user, room, secret, text);
    }
}
