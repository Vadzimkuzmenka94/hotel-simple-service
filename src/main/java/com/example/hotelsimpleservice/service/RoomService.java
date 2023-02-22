package com.example.hotelsimpleservice.service;

import com.example.hotelsimpleservice.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    Room createRoom(Room room);

    Room updateRoom(Room room, int id);

    Room findByRoomNumber(int number);

    List<Room> findRoomByDifferentParameters(Boolean wifi, Boolean free_parking, Boolean conditioner, Boolean fridge,
                                             Boolean no_smoking_room, Boolean breakfast, Boolean free);

    void takeRoom(int number);

    void detachEntity(Room room);
}