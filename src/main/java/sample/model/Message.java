package sample.model;

import org.json.simple.JSONObject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<MessageReceiveHistory> receiveHistory = new ArrayList<>();

    public List<MessageReceiveHistory> getReceiveHistory() {
        return receiveHistory;
    }

    public void setReceiveHistory(List<MessageReceiveHistory> receiveHistory) {
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
