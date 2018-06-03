package sample.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.Ranks;
import sample.model.User;
import sample.service.UserService;
import sample.service.session.WsSessionSender;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
    private final UserService userService;
    private final WsSessionSender wsSessionSender;
    private final List<AuthListener> listeners;

    @Autowired
    public AuthenticateServiceImpl(UserService userService, WsSessionSender wsSessionSender, List<AuthListener> listeners) {
        this.userService = userService;
        this.wsSessionSender = wsSessionSender;
        this.listeners = listeners;
    }


    @Override
    public boolean authenticate(String name, String hash) {
        Optional<Integer> mayBeRank = Ranks.getRank(name, hash);

        if (mayBeRank.isPresent()) {
            final User user = userService.findById(name).orElseGet(() -> {
                User createdUser = new User();
                createdUser.setName(name);
                createdUser.setRank(mayBeRank.get());
                return userService.save(createdUser);
            });
            listeners.forEach(l -> l.onSuccessAuth(user));
            return true;
        } else {
            wsSessionSender.sendToCurrent("Unauthorized");
            return false;
        }
    }
}
