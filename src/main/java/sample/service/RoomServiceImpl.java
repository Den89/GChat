package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.model.Room;
import sample.repository.RoomRepository;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Optional<Room> findById(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room create(String roomName) {
        return roomRepository.findById(roomName).orElseGet(() -> {
            Room room = new Room();
            room.setName(roomName);
            return roomRepository.save(room);
        });
    }


}
