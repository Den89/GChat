package sample.service.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.model.Room;
import sample.repository.RoomRepository;
import sample.service.room.RoomService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room getOrCreate(String roomName) {
        return roomRepository.findById(roomName).orElseGet(() -> {
            Room room = new Room();
            room.setName(roomName);
            return roomRepository.save(room);
        });
    }

    @Override
    public Map<Room, Integer> getRoomMessagesNumber() {
        Map<Room, Integer> numberByRoom = new HashMap<>();

        roomRepository.findAll().forEach(room -> {
            numberByRoom.put(room, room.getMessages().size());
        });

        return numberByRoom;
    }

}
