package com.example.hotelsimpleservice.service.implementation;

import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.repository.RoomRepository;
import com.example.hotelsimpleservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@Service
public class RoomServiceImplementation implements RoomService {
    private static final boolean TAKE_ROOM = false;
    private final RoomRepository roomRepository;
    private final EntityManager entityManager;

    @Autowired
    public RoomServiceImplementation(RoomRepository roomRepository, EntityManager entityManager) {
        this.roomRepository = roomRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Room createRoom(Room room) {
        if (roomRepository.findByRoomNumber(room.getRoomNumber()) != null) {
            throw new AppException(ErrorCode.ROOM_ALREADY_EXIST);
        }
        return roomRepository.save(room);
    }

    @Override
    public Room findByRoomNumber(int number) {
        checkingExistenceRoom(roomRepository.findByRoomNumber(number));
        return roomRepository.findByRoomNumber(number);
    }

    @Override
    public List<Room> findFreeNumber(boolean free) {
        return roomRepository.findFreeNumber(free);
    }

    @Override
    public List<Room> findRoomByDifferentParameters(Boolean wifi, Boolean free_parking,
                                                    Boolean conditioner, Boolean fridge, Boolean no_smoking_room,
                                                    Boolean breakfast, Boolean free) {
        return roomRepository.findRoomByDifferentParameters(wifi, free_parking, conditioner, fridge, no_smoking_room,
                breakfast, free);
    }

    @Transactional
    @Override
    public void takeRoom(int number) {
        checkingExistenceRoom(roomRepository.findByRoomNumber(number));
        roomRepository.takeRoom(number, TAKE_ROOM);
    }

    @Transactional
    @Override
    public Room updateRoom(Room room, int id) {
        checkingExistenceRoom(roomRepository.findByRoomNumber(id));
        Room roomInDb = roomRepository.findByRoomNumber(id);
        detachEntity(roomInDb);
        roomInDb.setId(room.getId() != null ? room.getId() : roomInDb.getId());
        roomInDb.setWifi(room.getWifi() != null ? room.getWifi() : roomInDb.getWifi());
        roomInDb.setFreeParking(room.getFreeParking() != null ? room.getFreeParking() : roomInDb.getFreeParking());
        roomInDb.setConditioner(room.getConditioner() != null ? room.getConditioner() : roomInDb.getConditioner());
        roomInDb.setFridge(room.getFridge() != null ? room.getFridge() : roomInDb.getFridge());
        roomInDb.setNoSmokingRoom(room.getNoSmokingRoom() != null ? room.getNoSmokingRoom() : roomInDb.getNoSmokingRoom());
        roomInDb.setBreakfast(room.getBreakfast() != null ? room.getBreakfast() : roomInDb.getBreakfast());
        roomInDb.setCost(room.getCost() != null ? room.getCost() : roomInDb.getCost());
        roomInDb.setComment(room.getComment() != null ? room.getComment() : roomInDb.getComment());
        roomInDb.setNumberOfBeds(room.getNumberOfBeds() != 0 ? room.getNumberOfBeds() : roomInDb.getNumberOfBeds());
        roomInDb.setFree(room.getFree() != null ? room.getFree() : roomInDb.getFree());
        roomInDb.setRoomNumber(room.getRoomNumber() != 0 ? room.getRoomNumber() : roomInDb.getRoomNumber());
        entityManager.merge(roomInDb);
        return roomInDb;
    }

    @Override
    public void detachEntity(Room room) {
        checkingExistenceRoom(room);
        entityManager.detach(room);
    }

    public void checkingExistenceRoom(Room room) {
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_NOT_FOUND);
        }
    }
}