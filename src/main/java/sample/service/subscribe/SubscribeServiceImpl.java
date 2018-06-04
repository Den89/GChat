package sample.service.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Room;
import sample.model.Subscription;
import sample.model.User;
import sample.repository.SubscriptionRepository;
import sample.service.listeners.events.NewSubscriptionEvent;

import java.time.LocalDateTime;


@Service
@Transactional
public class SubscribeServiceImpl implements SubscribeService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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
                    applicationEventPublisher.publishEvent(new NewSubscriptionEvent(user, room));
                    room.getSubscriptions().add(subscription);
                    return subscriptionRepository.save(subscription);
                });
    }
}
