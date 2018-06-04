package sample.service.listeners.events;

import sample.model.Room;
import sample.model.User;

public class SubscribeEvent {
    private User user;
    private Room room;

    public SubscribeEvent(User user, Room room) {
        this.user = user;
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
