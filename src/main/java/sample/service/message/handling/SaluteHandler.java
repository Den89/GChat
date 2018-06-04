package sample.service.message.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.service.UserService;
import sample.service.ws.WsSessionSender;

@Service
@Transactional
public class SaluteHandler implements MessageHandlingStrategy {
    private final UserService userService;
    private final WsSessionSender wsSessionSender;

    @Autowired
    public SaluteHandler(UserService userService, WsSessionSender wsSessionSender) {
        this.userService = userService;
        this.wsSessionSender = wsSessionSender;
    }

    @Override
    public void handle(MessageDto dto) {
        wsSessionSender.sendToCurrent("Your rank is " + userService.findById(dto.getName()).get().getRank());
    }

    @Override
    public MessageAction getAction() {
        return MessageAction.SALUTE;
    }
}
