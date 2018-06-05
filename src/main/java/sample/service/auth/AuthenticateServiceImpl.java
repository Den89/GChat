package sample.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Rank;
import sample.model.User;
import sample.service.listeners.events.FailAuthEvent;
import sample.service.listeners.events.SuccessAuthEvent;
import sample.service.user.RankService;
import sample.service.user.UserService;

import java.util.Optional;

@Service
@Transactional
public class AuthenticateServiceImpl implements AuthenticateService {
    private final UserService userService;
    private final RankService rankService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AuthenticateServiceImpl(UserService userService, RankService rankService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.rankService = rankService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public boolean checkAccess(String name, String hash) {
        Optional<Rank> mayBeRank = rankService.getRank(name, hash);

        if (mayBeRank.isPresent()) {
            final User user = userService.findByName(name).orElseGet(() -> {
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
