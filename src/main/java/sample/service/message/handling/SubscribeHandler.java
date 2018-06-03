package sample.service.message.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.model.Room;
import sample.service.RoomService;
import sample.service.UserService;
import sample.service.message.MessagingService;

@Service
public class SubscribeHandler implements MessageHandlingStrategy {
    @Autowired
    private MessagingService messagingService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;


    @Override
    public void handle(MessageDto dto) {
        Room room = roomService.getOrCreate(dto.getRoom());
        messagingService.subscribeUser(room, userService.findById(dto.getName()).get());
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.SUBSCRIBE;
    }
}
