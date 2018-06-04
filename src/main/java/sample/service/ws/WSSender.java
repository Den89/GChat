package sample.service.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.model.Message;
import sample.model.User;
import sample.service.listeners.FailAuthListener;
import sample.service.listeners.NewMessageListener;
import sample.service.listeners.SubscribeListener;
import sample.service.message.MessageService;
import sample.service.session.WsSessionManager;
import sample.service.listeners.events.SubscribeEvent;

import java.io.IOException;

@Service
@Transactional
public class WSSender implements WsSessionSender, SubscribeListener, FailAuthListener, NewMessageListener {
    private final WsSessionManager wsSessionManager;
    @Autowired
    private MessageService messageService;


    @Autowired
    public WSSender(WsSessionManager wsSessionManager) {
        this.wsSessionManager = wsSessionManager;
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
    public void onSubscribe(SubscribeEvent event) {
        sendMessageToSubscribers(constructSubscribeMessage(event));
    }

    @Override
    public void onSuccessFail(User user) {
        sendToCurrent("Unauthorized");
    }

    @Override
    public void onNewMessage(Message message) {
        sendMessageToSubscribers(message);
    }

    private Message constructSubscribeMessage(SubscribeEvent event) {
        return messageService.saveAndFlush(event.getUser(),
                event.getRoom(),
                false,
                "User " + event.getUser().getName() + " subscribed to the room " + event.getRoom().getName());
    }

    private void sendMessageToSubscribers(Message message) {
        message.getRoom().getUsers().forEach(user -> {
            if (message.isSecret() && user.getRank() < message.getUser().getRank()) {
                return;
            }
            sendToUser(user, message.toJSON().toJSONString());
        });
    }

    private void send(String text, WebSocketSession session) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(text));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
