package sample.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class WsSessionSenderImpl implements WsSessionSender {
    private final WsSessionManager wsSessionManager;

    @Autowired
    public WsSessionSenderImpl(WsSessionManager wsSessionManager) {
        this.wsSessionManager = wsSessionManager;
    }

    @Override
    public void send(String text) {
        WebSocketSession session = wsSessionManager.getCurrent();
        try {
            session.sendMessage(new TextMessage(text));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
