package sample.service;

import org.springframework.stereotype.Service;
import sample.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RoomServiceImpl implements RoomService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Room find() {
        return null;
    }

    @Override
    public Room create(String roomName) {
        Room room = null;
        if (roomName != null) {
            room = entityManager.find(Room.class, roomName);
            if (room == null) {
                room = new Room();
                room.setName(roomName);
                entityManager.persist(room);
            }
        }

        return room;
    }
}
