package sample.service;

import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.service.message.handling.MessageHandlingStrategy;
import sample.utils.MappingUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class WSHandler implements WebSocketHandler {
    private Map<MessageAction, MessageHandlingStrategy> handlersByAction;

    @Autowired
    public WSHandler(List<MessageHandlingStrategy> handlers) {
        Map<MessageAction, MessageHandlingStrategy> tempMap =
                handlers.stream().collect(Collectors.toMap(MessageHandlingStrategy::getAction, handler -> handler));
        this.handlersByAction = Collections.unmodifiableMap(new LinkedHashMap<>(tempMap));
    }

    @Autowired
    private AuthenticateService authService;
    @Autowired
    private RoomService roomService;

    private final ObjectReader messageReader = MappingUtils.readerFor(MessageDto.class);

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        MessageDto messageDto = messageReader.readValue(message.getPayload().toString());
        authService.authenticate(messageDto.getName(), messageDto.getHash(), session);
        roomService.create(messageDto.getRoom());
        handlersByAction.get(messageDto.getAction()).handle(messageDto);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
