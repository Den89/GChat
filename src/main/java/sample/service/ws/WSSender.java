package sample.service.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.dto.MessageDto;
import sample.model.Message;
import sample.model.MessageReceiveHistory;
import sample.model.User;
import sample.service.listeners.FailAuthListener;
import sample.service.listeners.NewMessageListener;
import sample.service.listeners.events.FailAuthEvent;
import sample.service.listeners.events.NewMessageEvent;
import sample.service.ws.session.WsSessionManager;
import sample.service.ws.session.WsSessionSender;
import sample.utils.MappingUtils;

import java.io.IOException;

@Service
public class WSSender implements WsSessionSender, FailAuthListener, NewMessageListener {
    private final WsSessionManager wsSessionManager;
    private final DozerBeanMapper mapper;
    private final ObjectWriter messageWriter = MappingUtils.writerFor(MessageDto.class);

    @Autowired
    public WSSender(WsSessionManager wsSessionManager, DozerBeanMapper mapper) {
        this.wsSessionManager = wsSessionManager;
        this.mapper = mapper;
    }

    @Override
    public void sendToCurrent(String text) {
        send(text, wsSessionManager.getCurrent());
    }

    @Override
    public void sendToUser(User user, String text) {
        send(text, wsSessionManager.getForLoggedUser(user));
    }

    @Override
    public void onSuccessFail(FailAuthEvent event) {
        sendToCurrent("Unauthorized");
    }

    @Override
    public void onNewMessage(NewMessageEvent event) {
        sendMessageToSubscribers(event.getMessage());
    }

    private void sendMessageToSubscribers(Message message) {
        final String json = toJSON(mapper.map(message, MessageDto.class));
        message.getReceiveHistory()
                .stream()
                .map(MessageReceiveHistory::getReceiver)
                .forEach(user -> sendToUser(user, json));
    }

    private String toJSON(MessageDto dto) {
        String json;
        try {
            json = messageWriter.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON writing exception", e);
        }
        return json;
    }

    private void send(String text, WebSocketSession session) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(text));
            } catch (IOException e) {
                throw new RuntimeException("Sending error", e);
            }
        }
    }

}
