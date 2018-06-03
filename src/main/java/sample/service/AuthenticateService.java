package sample.service;

import org.springframework.web.socket.WebSocketSession;
import sample.model.User;

public interface AuthenticateService {
    void authenticate(String name, String hash, WebSocketSession session);
}
