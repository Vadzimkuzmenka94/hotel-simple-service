package com.example.hotelsimpleservice.controller.entity_rest_controller;

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
@RequestMapping("/v-rest/bookings")
public class BookingRestController {
    private final String GET_BOOKING = "Link for get booking";
    private final String UPDATE_BOOKING = "Link for update booking";
    private final String DELETE_BOOKING = "Link for delete booking";
    private final BookingService bookingService;

    @Autowired
    public BookingRestController(BookingService bookingService, RoomService roomService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> findById(@PathVariable Long id) {
        generateResponseWithLinks(bookingService.findBookingById(id).get());
        return ResponseEntity.of(bookingService.findBookingById(id));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> findAll() {
        List<Booking> bookings = bookingService.findAllBookings();
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

    @GetMapping("/search")
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
        booking.add(linkTo(methodOn(BookingRestController.class).findById(booking.getId())).withRel(GET_BOOKING));
        booking.add(linkTo(methodOn(BookingRestController.class).delete(booking.getId())).withRel(DELETE_BOOKING));
        booking.add(linkTo(methodOn(BookingRestController.class).update(booking, booking.getId())).withRel(UPDATE_BOOKING));
        return booking;
    }

    public List<Booking> addLinkToEntity(List<Booking> bookings) {
        bookings.stream()
                .peek(this::generateResponseWithLinks)
                .collect(Collectors.toList());
        return bookings;
    }
}