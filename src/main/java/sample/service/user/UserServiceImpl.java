package sample.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.User;
import sample.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired private UserRepository repository;


    @Override
    public Optional<User> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
