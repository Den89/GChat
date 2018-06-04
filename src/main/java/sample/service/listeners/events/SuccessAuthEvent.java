package sample.service.listeners.events;

import sample.model.User;

public class SuccessAuthEvent {
    private User user;

    public SuccessAuthEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
