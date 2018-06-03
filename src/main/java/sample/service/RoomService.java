package sample.service;

import sample.model.Room;

import java.util.Optional;

public interface RoomService {
    Optional<Room> findById(String id);
    Room getOrCreate(String name);
}
