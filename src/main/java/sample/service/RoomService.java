package sample.service;

import sample.model.Room;

public interface RoomService {
    Room find();
    Room create(String name);
}
