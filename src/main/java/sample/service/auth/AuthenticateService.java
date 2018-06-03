package sample.service.auth;

import org.springframework.web.socket.WebSocketSession;
import sample.model.User;

public interface AuthenticateService {
    boolean authenticate(String name, String hash);
}
