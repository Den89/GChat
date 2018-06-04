package sample.service.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Room;
import sample.model.Subscription;
import sample.model.User;
import sample.repository.SubscriptionRepository;
import sample.service.message.MessageService;

import java.time.LocalDateTime;


@Service
@Transactional
public class SubscribeServiceImpl implements SubscribeService {
    private final SubscriptionRepository subscriptionRepository;
    private final MessageService messageService;

    @Autowired
    public SubscribeServiceImpl(SubscriptionRepository subscriptionRepository, MessageService messageService) {
        this.subscriptionRepository = subscriptionRepository;
        this.messageService = messageService;
    }

    @Override
    public Subscription subscribeIfNotSubscribed(Room room, User user) {
        return room
                .getSubscriptions()
                .stream()
                .filter(s -> s.getUser().equals(user) && s.getRoom().equals(room))
                .findFirst()
                .orElseGet(() -> {
                    Subscription subscription = new Subscription();
                    subscription.setRoom(room);
                    subscription.setUser(user);
                    subscription.setTime(LocalDateTime.now());
                    room.getSubscriptions().add(subscription);
                    messageService.saveAndPublish(user, room, false, getSubscriptionText(user, room));
                    return subscriptionRepository.save(subscription);
                });
    }

    private String getSubscriptionText(User user, Room room) {
        return "User " + user.getName() + " subscribed to the room " + room.getName();
    }
}
