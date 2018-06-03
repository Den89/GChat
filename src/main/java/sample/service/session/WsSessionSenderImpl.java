package sample.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.model.User;

import java.io.IOException;

@Service
public class WsSessionSenderImpl implements WsSessionSender {
    private final WsSessionManager wsSessionManager;

    @Autowired
    public WsSessionSenderImpl(WsSessionManager wsSessionManager) {
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
