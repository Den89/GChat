package sample.service.ws.session;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import sample.model.User;
import sample.service.listeners.SuccessAuthListener;
import sample.service.listeners.events.SuccessAuthEvent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WsSessionManagerImpl implements WsSessionManager, SuccessAuthListener {
    private final ThreadLocal<WebSocketSession> wsSession = new ThreadLocal<>();
    private final Map<User, WebSocketSession> sessionByUser = new ConcurrentHashMap<>();

    public WebSocketSession getCurrent() {
        return Optional
                .ofNullable(wsSession.get())
                .orElseThrow(() -> new RuntimeException("Could not obtain current session"));
    }

    @Override
    public WebSocketSession getForLoggedUser(User user) {
        return Optional
                .ofNullable(sessionByUser.get(user))
                .orElseThrow(() -> new RuntimeException("Could not obtain session for User " + user.getName()));
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
    public void onSuccessAuth(SuccessAuthEvent event) {
        User user = event.getUser();
        synchronized (user) {
            WebSocketSession userSession = sessionByUser.get(user);
            WebSocketSession currentSession = getCurrent();

            if (userSession == null) {
                setForUser(user, currentSession);
            } else if (userSession != currentSession) {
                throw new RuntimeException("Duplicate connection");
            }
        }
    }
}
