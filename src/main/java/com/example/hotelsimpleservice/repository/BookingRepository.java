package com.example.hotelsimpleservice.repository;

import com.example.hotelsimpleservice.model.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository <Booking, Long> {
    String FIND_BOOKING_BY_PARAMETERS = "SELECT id, " +
            "name, " +
            "duration, " +
            "cost, " +
            "currency, " +
            "room_number, " +
            "customer_id, " +
            "date, " +
            "start_booking, " +
            "finish_booking " +
            " FROM booking WHERE " +
            "(:name IS NULL OR booking.name =:name) AND " +
            "  (:duration IS NULL OR booking.duration =:duration) AND  " +
            "  (:cost IS NULL OR booking.cost =:cost) AND " +
            "  (:currency IS NULL OR booking.currency =:currency) AND  " +
            "  (:room_number IS NULL OR booking.room_number =:room_number) ";

    String FIND_BOOKING_BY_NAME = "SELECT id, " +
            "name, " +
            "duration, " +
            "cost, " +
            "currency, " +
            "room_number, " +
            "customer_id, " +
            "date, " +
            "start_booking, " +
            "finish_booking " +
            " FROM booking WHERE " +
            " booking.name =:name ";

    @Query(value = FIND_BOOKING_BY_PARAMETERS, nativeQuery = true)
    List<Booking> findBookingByDifferentParameters(String name, Integer duration, Double cost, String currency,
                                                   Integer room_number);

    @Query(value = FIND_BOOKING_BY_NAME, nativeQuery = true)
    List<Booking> findBookingByName(String name);
}