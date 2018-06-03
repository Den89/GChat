package sample.service.message.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.model.Room;
import sample.service.room.RoomService;
import sample.service.UserService;
import sample.service.message.MessagingService;
import sample.service.subscribe.SubscribeService;

@Service
@Transactional
public class SubscribeHandler implements MessageHandlingStrategy {
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;


    @Override
    public void handle(MessageDto dto) {
        Room room = roomService.getOrCreate(dto.getRoom());
        subscribeService.subscribeUser(room, userService.findById(dto.getName()).get());
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.SUBSCRIBE;
    }
}
