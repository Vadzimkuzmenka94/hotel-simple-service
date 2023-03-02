package com.example.hotelsimpleservice.controller.admin_page_controller;

import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class RoomAdminController {
    private final String ROOM_ATTRIBUTE = "room";
    private final RoomService roomService;

    @Autowired
    public RoomAdminController(RoomService roomService) {
        this.roomService = roomService;
    }


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

    @GetMapping("/create-room")
    public String createBookingPage(Model model) {
        model.addAttribute(ROOM_ATTRIBUTE, new Room());
        return "admin-page/create-room";
    }

    @PostMapping("/create-room")
    public String performCreatingRoom(@ModelAttribute("room") Room room) {
        roomService.createRoom(room);
        return "redirect:/admin/successful-create-room";
    }

    @GetMapping("/successful-create-room")
    public String SuccessfulRegistrationPage() {
        return "admin-page/successful-create-room";
    }

    @GetMapping("/update-room")
    public String updateBookingPage(Model model) {
        model.addAttribute(ROOM_ATTRIBUTE, new Room());
        return "admin-page/update-room";
    }

    @PostMapping("/update-room")
    public String performUpdatingRoom(@ModelAttribute("room") Room room) {
        roomService.updateRoom(room, room.getRoomNumber());
        return "redirect:/admin/successful-update-room";
    }

    @GetMapping("/successful-update-room")
    public String SuccessfulUpdatePage() {
        return "admin-page/successful-update-room";
    }
}