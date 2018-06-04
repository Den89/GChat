package sample.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntity{
    @Column(unique = true)
    private String name;
    @Column
    private int rank;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", rank=" + rank +
                '}';
    }
}
