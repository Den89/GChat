package sample.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String name;
    @Column
    private int rank;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Room> rooms = new HashSet<>();

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (rank != user.rank) return false;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + rank;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", rank=" + rank +
                '}';
    }
}
