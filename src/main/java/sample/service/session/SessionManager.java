package sample.service.session;

import org.springframework.web.socket.WebSocketSession;
import sample.model.User;

import java.util.Optional;

public interface SessionManager {
    Optional<WebSocketSession> getCurrent();

    void setCurrent(WebSocketSession session);

    void unsetCurrent(WebSocketSession session);

    Optional<WebSocketSession> getForLoggedUser(User user);

    void setForUser(User user, WebSocketSession session);
}
