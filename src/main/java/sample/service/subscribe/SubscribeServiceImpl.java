package sample.service.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Message;
import sample.model.Room;
import sample.model.User;
import sample.service.message.MessageService;
import sample.service.message.MessagingService;


@Service
@Transactional
public class SubscribeServiceImpl implements SubscribeService, SubscribeListener {
    @Autowired
    MessagingService messagingService;
    @Autowired
    MessageService messageService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void subscribeUser(Room room, User user) {
        if (room.getUsers().contains(user)) {
            return;
        }
        room.getUsers().add(user);
        user.getRooms().add(room);

        applicationEventPublisher.publishEvent(new SubscribeEvent(user, room));
    }

    @Override
    public void onSubscribe(SubscribeEvent event) {
        Message message = messageService.create(event.getUser(),
                event.getRoom(),
                false,
                "User " + event.getUser().getName() + " subscribed to the room " + event.getRoom().getName());
        messagingService.sendMessageToSubscribers(message);
        messagingService.addMessageInRoom(event.getRoom(), message);
    }
}
