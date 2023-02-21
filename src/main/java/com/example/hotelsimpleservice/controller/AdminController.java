package com.example.hotelsimpleservice.controller;

import com.example.hotelsimpleservice.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/show-all-actions")
    public String registrationPage() {
        return "auth/show-all-actions";
    }

    @GetMapping("/show-reporting-options")
    public String reportingPage() {
        return "auth/show-reporting-options";
    }

    @GetMapping("/show-booking-options")
    public String bookingPage() {
        return "auth/show-booking-options";
    }

    @GetMapping("/show-customer-options")
    public String customerPage() {
        return "auth/show-customer-options";
    }

    @GetMapping("/show-room-options")
    public String roomPage() {
        return "auth/show-room-options";
    }

    @GetMapping("/find-booking-by-id")
    public String findBookingPage() {
        return "auth/find-booking-by-id";
    }

    @GetMapping("/show-all-hotels")
    public String showHotels() {
        return "auth/show-all-hotels";
    }

    @GetMapping("/show-all-customers")
    public String showCustomers() {
        return "auth/show-all-customers";
    }

    @GetMapping("/find-customer-by-id")
    public String showCustomerById() {
        return "auth/find-customer-by-id";
    }
}