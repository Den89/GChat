package sample.model;

import org.json.simple.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Message extends BaseEntity{
    @Column
    private String text;
    @Column
    private LocalDateTime time;
    @Column
    private boolean secret;
    @OneToOne
    private User user;
    @ManyToOne
    private Room room;
    @OneToMany(mappedBy = "message", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Set<MessageReceiveHistory> receiveHistory = new HashSet<>();

    public Set<MessageReceiveHistory> getReceiveHistory() {
        return receiveHistory;
    }

    public void setReceiveHistory(Set<MessageReceiveHistory> receiveHistory) {
        this.receiveHistory = receiveHistory;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("action", "message");
        result.put("text", text);
        result.put("epoch", time);
        result.put("room", getRoom().getName());
        result.put("author", user.getName());
        result.put("secret", secret);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + getId() +
                ", text='" + text + '\'' +
                ", epoch=" + time +
                ", secret=" + secret +
                ", user=" + user.getName() +
                ", room=" + room.getName() +
                '}';
    }
}
