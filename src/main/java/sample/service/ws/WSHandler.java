package sample.service.ws;

import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.dto.MessageDto;
import sample.model.MessageAction;
import sample.service.auth.AuthenticateService;
import sample.service.message.handling.MessageHandlingStrategy;
import sample.service.ws.session.WsSessionManager;
import sample.utils.MappingUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class WSHandler implements WebSocketHandler {
    private Map<MessageAction, MessageHandlingStrategy> handlersByAction;
    private final AuthenticateService authService;
    private final WsSessionManager wsSessionManager;
    private final ObjectReader messageReader = MappingUtils.readerFor(MessageDto.class);

    @Autowired
    public WSHandler(List<MessageHandlingStrategy> handlers, AuthenticateService authService, WsSessionManager wsSessionManager) {
        this.handlersByAction = toImmutableMapByAction(handlers);
        this.authService = authService;
        this.wsSessionManager = wsSessionManager;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        wsSessionManager.setCurrent(session);
        MessageDto messageDto = messageReader.readValue(message.getPayload().toString());
        authService.checkAccess(messageDto.getName(), messageDto.getHash());
        handlersByAction.get(messageDto.getAction()).handle(messageDto);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        wsSessionManager.setCurrent(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        wsSessionManager.unsetCurrent(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        wsSessionManager.unsetCurrent(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private Map<MessageAction, MessageHandlingStrategy> toImmutableMapByAction(List<MessageHandlingStrategy> handlers) {
        Map<MessageAction, MessageHandlingStrategy> tempMap =
                handlers.stream().collect(Collectors.toMap(MessageHandlingStrategy::getAction, handler -> handler));
        return Collections.unmodifiableMap(new LinkedHashMap<>(tempMap));
    }
}
