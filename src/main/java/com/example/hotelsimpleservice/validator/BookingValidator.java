package com.example.hotelsimpleservice.validator;

import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BookingValidator {
    private final String BYN_CURRENCY = "BYN";
    private final String LARI_CURRENCY = "LARI";
    private final RoomService roomService;

    @Autowired
    public BookingValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    public void validate(Booking booking) {
        if (!(Objects.equals(booking.getCurrency(), BYN_CURRENCY) ||
                Objects.equals(booking.getCurrency(), LARI_CURRENCY)) &&
                !booking.getCurrency().isEmpty()) {
            throw new AppException(ErrorCode.BOOKING_CURRENCY_INVALID);
        }
        if (roomService.findByRoomNumber(booking.getRoomNumber()) == null) {
            throw new AppException(ErrorCode.ROOM_NOT_FOUND);
        }
        if (!roomService.findByRoomNumber(booking.getRoomNumber()).getFree()) {
            throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE);
        }
    }
}