package com.example.hotelsimpleservice.service;

import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookingService {
    Booking createBooking(Booking booking);

    Optional<Booking> findById(Long id);

    List<Booking> findAll();

    List<Booking> findByName(String name);

    Booking updateBooking(Booking booking, Long id);

    void deleteBooking(Long id);

    List<Booking> findBookingByDifferentParameters(String name, Integer duration, Double cost, String currency,
                                             Integer room_number);

    Double calculateBookingCost(int numberOfDays, double cost);

    Double recalculationToCorrectCurrency(String currency, Double price);

    void calculateFinalPrice(Booking booking);
}