package sample.service.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Room;
import sample.model.User;
import sample.service.listeners.events.SubscribeEvent;
import sample.service.message.MessageService;
import sample.service.message.MessagingService;


@Service
@Transactional
public class SubscribeServiceImpl implements SubscribeService{
    @Autowired
    MessagingService messagingService;
    @Autowired
    MessageService messageService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void subscribeIfNotSubscribed(Room room, User user) {
        if (room.getUsers().contains(user)) {
            return;
        }
        room.getUsers().add(user);
        user.getRooms().add(room);

        applicationEventPublisher.publishEvent(new SubscribeEvent(user, room));
    }
}
