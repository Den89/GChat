package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import sample.Ranks;
import sample.model.User;
import sample.service.message.MessagingService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
    @Autowired
    MessagingService messagingService;

    @Autowired
    UserService userService;


    @Autowired
    List<AuthListener> listeners;


    @Override
    public void authenticate(String name, String hash, WebSocketSession session) {
        User user = null;
        try {
            user = getUser(name, hash);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (user == null) {
            messagingService.sendToCurrent("Unauthorized");

        } else {
            final User userForLambda = user;
            listeners.forEach(l -> l.onSuccessAuth(userForLambda));
        }


    }

    private User getUser(String name, String hash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.findById(name).orElseGet(() -> {
            Optional<Integer> maybeRank = null;
            try {
                maybeRank = Ranks.getRank(name, hash);
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (!maybeRank.isPresent()) {
                return null;
            }

            User user = new User();
            user.setName(name);
            user.setRank(maybeRank.get());
            return userService.save(user);
        });
    }
}
