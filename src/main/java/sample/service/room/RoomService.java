package sample.service.room;

import sample.model.Room;

import java.util.Map;

public interface RoomService {
    Map<Room, Integer> getRoomMessagesNumber();
    Room getOrCreate(String name);
}
