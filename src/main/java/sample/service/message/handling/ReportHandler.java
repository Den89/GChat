package sample.service.message.handling;

import org.springframework.stereotype.Service;
import sample.dto.MessageDto;
import sample.model.MessageAction;

@Service
public class ReportHandler implements MessageHandlingStrategy {
    @Override
    public void handle(MessageDto dto) {
        messagingService.report(user, room, messageText, secret, sessionByUser);
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.REPORT;
    }

}
