package sample.service;

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
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
