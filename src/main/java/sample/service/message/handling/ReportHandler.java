package sample.service.message.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.model.Room;
import sample.service.message.MessagingService;
import sample.service.room.RoomService;
import sample.service.user.UserService;

@Service
@Transactional
public class ReportHandler implements MessageHandlingStrategy {
    private final MessagingService messagingService;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public ReportHandler(MessagingService messagingService, UserService userService, RoomService roomService) {
        this.messagingService = messagingService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public void handle(MessageDto dto) {
        Room room = roomService.getOrCreate(dto.getRoom());
        messagingService.report(userService.findByName(dto.getName()).get(), room, dto.getMessage(), dto.isSecret());
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.REPORT;
    }

}
