package sample.service.message;

import sample.model.Message;
import sample.model.Room;
import sample.model.User;

import java.util.List;

public interface MessageService {
    List<Message> findAll();
    Message saveAndPublish(User user, Room room, boolean secret, String text);
}
