package com.example.hotelsimpleservice.controller.general_controller;

import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final String Booking_ATTRIBUTE = "booking";
    private final BookingService bookingService;
    private final MailSender javaMailSender;

    @Autowired
    public BookingController(BookingService bookingService, MailSender javaMailSender) {
        this.bookingService = bookingService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/create-booking")
    public String registrationPage(Model model) {
        model.addAttribute(Booking_ATTRIBUTE, new Booking());
        return "general-page/create-booking";
    }


    @PostMapping("/create-booking")
    public String performRegistration(@ModelAttribute("booking") Booking booking) {
        bookingService.createBooking(booking);
        return "redirect:/auth/successful-registration";
    }
}