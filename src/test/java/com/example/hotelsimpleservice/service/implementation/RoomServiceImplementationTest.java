package com.example.hotelsimpleservice.service.implementation;

import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.repository.RoomRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoomServiceImplementationTest {
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private RoomServiceImplementation roomServiceImplementation;

    private Room room;

    @BeforeEach
    public void setUp() {
        room = new Room();
        room.setId(1L);
        room.setRoomNumber(100);
        room.setWifi(true);
        room.setFreeParking(true);
        room.setConditioner(true);
        room.setFridge(true);
        room.setNoSmokingRoom(false);
        room.setBreakfast(false);
        room.setCost(100.0);
        room.setComment("Nice room");
        room.setNumberOfBeds(2);
        room.setFree(false);
    }

    @Test
    public void testSave() {
        when(roomRepository.save(room)).thenReturn(room);

        Room savedRoom = roomServiceImplementation.createRoom(room);

        verify(roomRepository, times(1)).save(room);
        assertEquals(room, savedRoom);
    }

    @Test
    public void testReadByRoomNumber() {
        Room room = new Room(1L, true, false, false, false, false, false, 100D, "Test Comment", 2, true, 2);
        Room roomInDb = new Room(2L, true, true, true, true, true, true, 200D, "Test Comment 2", 2, false, 2);
        when(roomRepository.findByRoomNumber(roomInDb.getRoomNumber())).thenReturn(room);
        Room readRoom = roomServiceImplementation.findByRoomNumber(room.getRoomNumber());

        verify(roomRepository, times(2)).findByRoomNumber(room.getRoomNumber());
        assertEquals(room, readRoom);
    }


    @Test
    public void testUpdateRoom() {

        Room room = new Room(1L, true, false, false, false, false, false, 100D, "Test Comment", 2, true, 2);
        Room roomInDb = new Room(2L, true, true, true, true, true, true, 200D, "Test Comment 2", 2, false, 2);
        when(roomRepository.findByRoomNumber(2)).thenReturn(roomInDb);

        Room updatedRoom = roomServiceImplementation.updateRoom(room, 2);

        assertEquals(room.getId(), updatedRoom.getId());
        assertEquals(room.getWifi(), updatedRoom.getWifi());
        assertEquals(room.getFreeParking(), updatedRoom.getFreeParking());
        assertEquals(room.getConditioner(), updatedRoom.getConditioner());
        assertEquals(room.getFridge(), updatedRoom.getFridge());
        assertEquals(room.getNoSmokingRoom(), updatedRoom.getNoSmokingRoom());
        assertEquals(room.getBreakfast(), updatedRoom.getBreakfast());
        assertEquals(room.getCost(), updatedRoom.getCost());
        assertEquals(room.getComment(), updatedRoom.getComment());
        assertEquals(room.getNumberOfBeds(), updatedRoom.getNumberOfBeds());
        assertEquals(room.getFree(), updatedRoom.getFree());
        assertEquals(room.getRoomNumber(), updatedRoom.getRoomNumber());
    }

    @Test
    public void testDetachRoom() {

        Room room = new Room(1L, true, false, false, false, false, false, 100D, "Test Comment", 2, true, 2);

        roomServiceImplementation.detachEntity(room);

        verify(entityManager, times(1)).detach(room);
    }

    @Test
    public void testTakeRoom() {

        int number = 2;
        Room room = new Room(1L, true, false, false, false, false, false, 100D, "Test Comment", 2, true, 2);
        when(roomRepository.findByRoomNumber(2)).thenReturn(room);
        roomServiceImplementation.takeRoom(number);
        verify(roomRepository, times(1)).takeRoom(number, false);
    }

    @Test
    public void testFindRoomByDifferentParameters() {
        boolean wifi = false;
        boolean freeParking = false;
        boolean conditioner = false;
        boolean fridge = false;
        boolean noSmokingRoom = false;
        boolean breakfast = false;
        boolean free = false;
        List<Room> expectedRooms = Arrays.asList(
                new Room(1L, false, false, false, false, false, false, 100D, "Test Comment", 2, false, 2),
                new Room(2L, false, false, false, false, false, false, 200D, "Test Comment 2", 2, false, 2)
        );
        when(roomRepository.findRoomByDifferentParameters(wifi, freeParking, conditioner, fridge, noSmokingRoom, breakfast, free)).thenReturn(expectedRooms);
    }

    @Test
    public void testUpdate() {
        int id = 123;
        Room room = mock(Room.class);
        Room roomInDb = mock(Room.class);
        when(roomRepository.findByRoomNumber(id)).thenReturn(roomInDb);
        roomServiceImplementation.updateRoom(room, id);
        verify(entityManager).merge(roomInDb);
    }

    @Test
    public void testDetach() {
        Room room = mock(Room.class);
        roomServiceImplementation.detachEntity(room);
        verify(entityManager).detach(room);
    }

    @Test(expected = AppException.class)
    public void testCheckingExistenceRoom() {
        Room room = null;
        roomServiceImplementation.checkingExistenceRoom(room);
    }
}