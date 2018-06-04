package sample.service;

import sample.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByName(String id);
    User save(User user);
}
