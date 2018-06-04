package sample.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Room extends BaseEntity{
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private Set<Subscription> subscriptions = new HashSet<>();

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                '}';
    }
}
