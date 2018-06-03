package sample.service.session;

import sample.model.User;

public interface WsSessionSender {
    void sendToCurrent(String text);
    void sendToUser(User user, String text);
}
