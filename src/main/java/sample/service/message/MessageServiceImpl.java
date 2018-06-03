package sample.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Message;
import sample.model.Room;
import sample.model.User;
import sample.repository.MessageRepository;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message create(User user, Room room, boolean secret, String text) {
        Message message = new Message();
        message.setUser(user);
        message.setRoom(room);
        message.setEpoch(System.currentTimeMillis() / 1000);
        message.setSecret(secret);
        message.setText(text);
        return messageRepository.save(message);
    }
}
