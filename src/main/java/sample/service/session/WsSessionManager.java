package sample.service.session;

import org.springframework.web.socket.WebSocketSession;
import sample.model.User;

import java.util.Optional;

public interface WsSessionManager {
    WebSocketSession getCurrent();

    void setCurrent(WebSocketSession session);

    void unsetCurrent(WebSocketSession session);

    WebSocketSession getForLoggedUser(User user);

    void setForUser(User user, WebSocketSession session);
}
