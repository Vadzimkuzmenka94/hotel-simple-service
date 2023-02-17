package com.example.hotelsimpleservice.controller;

import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.service.BookingService;
import com.example.hotelsimpleservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;

    @Autowired
    public BookingController(BookingService bookingService, RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id) {
        generateResponseWithLinks(bookingService.findById(id).get());
        return ResponseEntity.of(bookingService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAll() {
        List<Booking> bookings = bookingService.findAll();
        addLinkToEntity(bookings);
        return ResponseEntity.ok().body(bookings);
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Booking>> findBookingByParameter(@RequestParam(required = false) String name,
                                                                @RequestParam(required = false) Integer duration,
                                                                @RequestParam(required = false) Double cost,
                                                                @RequestParam(required = false) String currency,
                                                                @RequestParam(required = false) Integer room_number) {
        List<Booking> bookings = bookingService.findBookingByDifferentParameters(
                name, duration, cost, currency, room_number);
        addLinkToEntity(bookings);
        return ResponseEntity.ok().body(bookings);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Booking> update(@RequestBody Booking booking,
                                          @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookingService.updateBooking(booking, id));
    }

    public Booking generateResponseWithLinks(Booking booking) {
        booking.add(linkTo(methodOn(BookingController.class).getById(booking.getId())).withRel("Link for get booking"));
        booking.add(linkTo(methodOn(BookingController.class).delete(booking.getId())).withRel("Link for delete booking"));
        booking.add(linkTo(methodOn(BookingController.class).update(booking, booking.getId())).withRel("Link for update booking"));
        return booking;
    }

    public List<Booking> addLinkToEntity(List<Booking> bookings) {
        bookings.stream()
                .peek(this::generateResponseWithLinks)
                .collect(Collectors.toList());
        return bookings;
    }
}