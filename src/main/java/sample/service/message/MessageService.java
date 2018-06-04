package sample.service.message;

import sample.model.Message;
import sample.model.Room;
import sample.model.User;

public interface MessageService {
    Message save(User user, Room room, boolean secret, String text);
    Message saveAndFlush(User user, Room room, boolean secret, String text);
}
