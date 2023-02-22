package com.example.hotelsimpleservice.controller.admin_page_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/show-all-actions")
    public String registrationPage() {
        return "admin-page/show-all-actions";
    }

    @GetMapping("/show-reporting-options")
    public String reportingPage() {
        return "admin-page/show-reporting-options";
    }

    @GetMapping("/show-booking-options")
    public String bookingPage() {
        return "admin-page/show-booking-options";
    }

    @GetMapping("/show-customer-options")
    public String customerPage() {
        return "admin-page/show-customer-options";
    }

    @GetMapping("/show-room-options")
    public String roomPage() {
        return "admin-page/show-room-options";
    }

    @GetMapping("/find-booking-by-id")
    public String findBookingPage() {
        return "admin-page/find-booking-by-id";
    }

    @GetMapping("/show-all-hotels")
    public String showHotels() {
        return "admin-page/show-all-hotels";
    }

    @GetMapping("/show-all-customers")
    public String showCustomers() {
        return "admin-page/show-all-customers";
    }

    @GetMapping("/find-customer-by-id")
    public String showCustomerById() {
        return "admin-page/find-customer-by-id";
    }
}