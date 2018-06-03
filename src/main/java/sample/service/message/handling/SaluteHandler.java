package sample.service.message.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import sample.Ranks;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.service.UserService;
import sample.service.message.MessagingService;

@Service
public class SaluteHandler implements MessageHandlingStrategy {
    private final MessagingService messagingService;
    private final UserService userService;

    @Autowired
    public SaluteHandler(MessagingService messagingService, UserService userService) {
        this.messagingService = messagingService;
        this.userService = userService;
    }

    @Override
    public void handle(MessageDto dto) {
        messagingService.sendToCurrent("Your rank is " + userService.findById(dto.getName()).get().getRank());
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.SALUTE;
    }
}
