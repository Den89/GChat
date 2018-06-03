package sample.service.session;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import sample.model.User;
import sample.service.AuthListener;
import sample.service.session.SessionManager;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManagerImpl implements SessionManager, AuthListener {
    private final ThreadLocal<WebSocketSession> wsSession = new ThreadLocal<>();
    private final Map<User, WebSocketSession> sessionByUser = new ConcurrentHashMap<>();

    public Optional<WebSocketSession> getCurrent() {
        return Optional.ofNullable(wsSession.get());
    }

    @Override
    public Optional<WebSocketSession> getForLoggedUser(User user) {
        return Optional.ofNullable(sessionByUser.get(user));
    }

    @Override
    public void setForUser(User user, WebSocketSession session) {
        sessionByUser.put(user, session);
    }

    public void setCurrent(WebSocketSession session) {
        wsSession.set(session);
    }

    public void unsetCurrent(WebSocketSession session) {
        wsSession.remove();
    }

    @Override
    public void onSuccessAuth(User user) {
        setForUser(user, getCurrent().get());
    }
}
