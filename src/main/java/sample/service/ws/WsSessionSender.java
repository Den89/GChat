package sample.service.ws;

import sample.model.User;

public interface WsSessionSender {
    void sendToCurrent(String text);
    void sendToUser(User user, String text);
}
