package sample.service.message.handling;

import sample.dto.MessageDto;
import sample.model.MessageAction;

public interface MessageHandlingStrategy {
    void handle(MessageDto dto);
    MessageAction getAction();
}
