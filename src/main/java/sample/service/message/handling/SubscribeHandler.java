package sample.service.message.handling;

import org.springframework.stereotype.Service;
import sample.dto.MessageDto;
import sample.model.MessageAction;

@Service
public class SubscribeHandler implements MessageHandlingStrategy {
    @Override
    public void handle(MessageDto dto) {
        messagingService.subscribeUser(room, user, sessionByUser);
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.SUBSCRIBE;
    }
}
