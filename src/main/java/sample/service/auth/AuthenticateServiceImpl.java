package sample.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.Ranks;
import sample.model.User;
import sample.service.UserService;
import sample.service.listeners.events.SuccessAuthEvent;
import sample.service.listeners.events.FailAuthEvent;

import java.util.Optional;

@Service
@Transactional
public class AuthenticateServiceImpl implements AuthenticateService {
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AuthenticateServiceImpl(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public boolean checkAccess(String name, String hash) {
        Optional<Integer> mayBeRank = Ranks.getRank(name, hash);

        if (mayBeRank.isPresent()) {
            final User user = userService.findById(name).orElseGet(() -> {
                User createdUser = new User();
                createdUser.setName(name);
                createdUser.setRank(mayBeRank.get());
                return userService.save(createdUser);
            });
            applicationEventPublisher.publishEvent(new SuccessAuthEvent(user));
            return true;
        } else {
            applicationEventPublisher.publishEvent(new FailAuthEvent());
            return false;
        }
    }
}
