package com.example.hotelsimpleservice.service;

import com.example.hotelsimpleservice.model.Booking;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookingService {
    Booking createBooking(Booking booking);

    Optional<Booking> findBookingById(Long id);

    List<Booking> findAllBookings();

    List<Booking> findBookingByName(String name);

    Booking updateBooking(Booking booking, Long id);

    void deleteBooking(Long id);

    List<Booking> findBookingByDifferentParameters(String name, Integer duration, Double cost, String currency,
                                             Integer room_number);

    Double calculateBookingCost(int numberOfDays, double cost);

    Double recalculationToCorrectCurrency(String currency, Double price);

    void calculateFinalPrice(Booking booking);
}