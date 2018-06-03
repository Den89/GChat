package sample.service;

import sample.model.User;

public interface AuthListener {
    void onSuccessAuth(User user);
    void onAuthFailed();
}
