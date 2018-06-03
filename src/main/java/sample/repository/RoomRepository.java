package sample.repository;

import org.springframework.data.repository.CrudRepository;
import sample.model.Room;

public interface RoomRepository extends CrudRepository<Room, String> {
}
