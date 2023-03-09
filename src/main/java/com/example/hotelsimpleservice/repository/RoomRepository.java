package com.example.hotelsimpleservice.repository;

import com.example.hotelsimpleservice.model.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableTransactionManagement
public interface RoomRepository extends CrudRepository<Room, Long> {
    String FIND_BY_ROOM_NUMBER_QUERY = "SELECT id, " +
            "wifi, " +
            "free_parking, " +
            "conditioner, " +
            "fridge, " +
            "no_smoking_room, " +
            "breakfast, " +
            "cost, " +
            "comment, " +
            "number_of_beds, " +
            "free, " +
            "room_number " +
            "FROM public.rooms WHERE public.rooms.room_number = :number";

    String FIND_FREE_ROOM = "SELECT id, " +
            "wifi, " +
            "free_parking, " +
            "conditioner, " +
            "fridge, " +
            "no_smoking_room, " +
            "breakfast, " +
            "cost, " +
            "comment, " +
            "number_of_beds, " +
            "free, " +
            "room_number " +
            "FROM rooms WHERE rooms.free = :free";

    String CHANGE_ROOM_STATUS_QUERY = "UPDATE public.rooms SET free =:free where room_number =:number";

    String FIND_ROOM_BY_PARAMETERS = "SELECT id, " +
            "wifi, " +
            "free_parking, " +
            "conditioner, " +
            "fridge, " +
            "no_smoking_room, " +
            "breakfast, " +
            "cost, " +
            "comment, " +
            "number_of_beds, " +
            "free, " +
            "room_number " +
            " from rooms where" +
            "(:wifi IS NULL OR rooms.wifi =:wifi) AND " +
            "  (:free_parking IS NULL OR rooms.free_parking =:free_parking) AND   " +
            "  (:conditioner IS NULL OR rooms.conditioner =:conditioner) AND   " +
            "  (:fridge IS NULL OR rooms.fridge =:fridge) AND   " +
            "  (:no_smoking_room IS NULL OR rooms.no_smoking_room =:no_smoking_room) AND   " +
            "  (:breakfast IS NULL OR rooms.breakfast =:breakfast) AND  " +
            "  (:free IS NULL OR rooms.free =:free) ";


    @Query(value = FIND_BY_ROOM_NUMBER_QUERY, nativeQuery = true)
    Room findRoomByNumber(int number);

    @Query(value = FIND_FREE_ROOM, nativeQuery = true)
    List<Room> findFreeRoom(boolean free);

    @Query(value = FIND_ROOM_BY_PARAMETERS, nativeQuery = true)
    List<Room> findRoomByDifferentParameters(Boolean wifi, Boolean free_parking, Boolean conditioner, Boolean fridge,
                                             Boolean no_smoking_room, Boolean breakfast, Boolean free);

    @Modifying
    @Query(value = CHANGE_ROOM_STATUS_QUERY, nativeQuery = true)
    @Transactional
    void takeRoom(@Param("number") int number, @Param("free") boolean free);
}