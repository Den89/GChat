package sample.service.auth;

import org.springframework.web.socket.WebSocketSession;
import sample.model.User;

public interface AuthenticateService {
    boolean checkAccess(String name, String hash);
}
