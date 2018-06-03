package sample.service.message.handling;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import sample.Ranks;
import sample.dto.MessageDto;
import sample.model.MessageAction;

@Service
public class SaluteHandler implements MessageHandlingStrategy{
    @Override
    public void handle(MessageDto dto) {
        session.sendMessage(new TextMessage("Your rank is " + Ranks.getRankName(user.getRank())));
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.SALUTE;
    }
}
