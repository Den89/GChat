package sample.service.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.model.Message;
import sample.model.MessageReceiveHistory;
import sample.model.User;
import sample.service.listeners.FailAuthListener;
import sample.service.listeners.NewMessageListener;
import sample.service.listeners.events.FailAuthEvent;
import sample.service.listeners.events.NewMessageEvent;
import sample.service.ws.session.WsSessionManager;
import sample.service.ws.session.WsSessionSender;

import java.io.IOException;

@Service
public class WSSender implements WsSessionSender, FailAuthListener, NewMessageListener {
    private final WsSessionManager wsSessionManager;

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
    public void onSuccessFail(FailAuthEvent event) {
        sendToCurrent("Unauthorized");
    }

    @Override
    public void onNewMessage(NewMessageEvent event) {
        sendMessageToSubscribers(event.getMessage());
    }

    private void sendMessageToSubscribers(Message message) {
        message.getReceiveHistory().stream().map(MessageReceiveHistory::getReceiver).forEach(user -> {
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
