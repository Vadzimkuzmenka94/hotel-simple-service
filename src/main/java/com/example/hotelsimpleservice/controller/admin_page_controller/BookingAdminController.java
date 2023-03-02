package com.example.hotelsimpleservice.controller.admin_page_controller;

import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookingAdminController {
    private final String BOOKING_ATTRIBUTE = "booking";
    private final BookingService bookingService;

    public BookingAdminController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @GetMapping("/delete-booking")
    public String deleteBookingPage(Model model) {
        model.addAttribute(BOOKING_ATTRIBUTE, new Booking());
        return "admin-page/delete-booking";
    }


    @DeleteMapping("/delete-booking")
    public String performDeletingCustomer(@ModelAttribute(BOOKING_ATTRIBUTE) Booking booking) {
        bookingService.deleteBooking(booking.getId());
        return "redirect:/admin/successful-delete-booking";
    }

    @GetMapping("/successful-delete-booking")
    public String SuccessfulDeleteBookingPage() {
        return "admin-page/successful-delete-booking";
    }

    @GetMapping("/find-booking")
    public String findBookingPage(Model model) {
        model.addAttribute(BOOKING_ATTRIBUTE, new Booking());
        return "admin-page/find-booking-by-parameter";
    }

    @PostMapping(value = "/find-booking")
    public ModelAndView findBookingByParameter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer duration,
            @RequestParam(required = false) Double cost,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) Integer room_number) {
        List<Booking> bookings = bookingService.findBookingByDifferentParameters(name, duration, cost,
                currency, room_number);
        ModelAndView model = new ModelAndView(BOOKING_ATTRIBUTE);
        model.addObject(BOOKING_ATTRIBUTE, bookings);
        return model;
    }
}