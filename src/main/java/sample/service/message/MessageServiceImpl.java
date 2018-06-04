package sample.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Message;
import sample.model.Room;
import sample.model.User;
import sample.repository.MessageRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message save(User user, Room room, boolean secret, String text) {
        return messageRepository.save(createMessage(user, room, secret, text));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Message saveAndFlush(User user, Room room, boolean secret, String text) {
        return messageRepository.saveAndFlush(createMessage(user, room, secret, text));
    }

    private Message createMessage(User user, Room room, boolean secret, String text) {
        Message message = new Message();
        message.setUser(user);
        message.setRoom(room);
        message.setTime(LocalDateTime.now());
        message.setSecret(secret);
        message.setText(text);
        return message;
    }
}
