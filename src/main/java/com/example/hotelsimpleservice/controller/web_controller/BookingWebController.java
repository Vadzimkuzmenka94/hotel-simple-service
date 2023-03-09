package com.example.hotelsimpleservice.controller.web_controller;

import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.emailNotifications.Messages;
import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/bookings")
public class BookingWebController {
    private final String BOOKING_ATTRIBUTE = "booking";
    private final BookingService bookingService;
    private final MailSender mailSender;

    public BookingWebController(BookingService bookingService, MailSender mailSender) {
        this.bookingService = bookingService;
        this.mailSender = mailSender;
    }

    @GetMapping("/delete")
    public String deleteBookingPage(Model model) {
        model.addAttribute(BOOKING_ATTRIBUTE, new Booking());
        return "pages/booking-pages/delete-booking";
    }

    @DeleteMapping("/delete")
    public String performDeletingBooking(@ModelAttribute(BOOKING_ATTRIBUTE) Booking booking) {
        mailSender.sendEmail(booking.getCustomer().getEmail(), "message", Messages.DELETE_BOOKING_MESSAGE.getMessage());
        log.info(Messages.DELETE_BOOKING_MESSAGE.getMessage());
        bookingService.deleteBooking(booking.getId());
        return "redirect:pages/booking-pages/successful-pages/successful-delete-booking";
    }

    @GetMapping("/successful-delete")
    public String SuccessfulDeleteBookingPage() {
        return "pages/booking-pages/successful-pages/successful-delete-booking";
    }

    @GetMapping("/search")
    public String findBookingPage(Model model) {
        model.addAttribute(BOOKING_ATTRIBUTE, new Booking());
        return "pages/booking-pages/find-booking-by-parameter";
    }

    @PostMapping("/search")
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

    @GetMapping("/show-options")
    public String bookingPage() {
        return "pages/booking-pages/show-booking-options";
    }

    @GetMapping("/search-by-id")
    public String findBookingPage() {
        return "pages/booking-pages/find-booking-by-id";
    }

    @GetMapping("/create")
    public String registrationPage(Model model) {
        model.addAttribute(BOOKING_ATTRIBUTE, new Booking());
        return "pages/booking-pages/create-booking";
    }

    @PostMapping("/create")
    public String performCreatingBooking(@ModelAttribute(BOOKING_ATTRIBUTE) Booking booking) {
        mailSender.sendEmail(booking.getCustomer().getEmail(), "message", Messages.CREATE_BOOKING_MESSAGE.getMessage());
        log.info(Messages.CREATE_BOOKING_MESSAGE.getMessage());
        bookingService.createBooking(booking);
        return "redirect:pages/booking-pages/successful-pages/successful-create-booking";
    }
}