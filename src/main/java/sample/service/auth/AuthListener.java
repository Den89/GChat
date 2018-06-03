package sample.service.auth;

import sample.model.User;

public interface AuthListener {
    void onSuccessAuth(User user);
}
