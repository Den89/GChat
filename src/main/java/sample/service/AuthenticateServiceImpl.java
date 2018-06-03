package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import sample.Ranks;
import sample.model.User;
import sample.service.message.MessagingService;
import sample.service.session.WsSessionSender;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
    private final MessagingService messagingService;
    private final UserService userService;
    private final WsSessionSender wsSessionSender;
    private final List<AuthListener> listeners;

    @Autowired
    public AuthenticateServiceImpl(MessagingService messagingService, UserService userService, WsSessionSender wsSessionSender, List<AuthListener> listeners) {
        this.messagingService = messagingService;
        this.userService = userService;
        this.wsSessionSender = wsSessionSender;
        this.listeners = listeners;
    }


    @Override
    public void authenticate(String name, String hash, WebSocketSession session) {
        User user = null;
        try {
            user = getUser(name, hash);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (user == null) {
            wsSessionSender.send("Unauthorized");

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
