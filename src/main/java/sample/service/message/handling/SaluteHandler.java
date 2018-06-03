package sample.service.message.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import sample.Ranks;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.service.message.MessagingService;

@Service
public class SaluteHandler implements MessageHandlingStrategy{
    @Autowired
    MessagingService messagingService;

    @Override
    public void handle(MessageDto dto) {
        messagingService.sendToCurrent(new TextMessage("Your rank is " + Ranks.getRankName(user.getRank())));
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.SALUTE;
    }
}
