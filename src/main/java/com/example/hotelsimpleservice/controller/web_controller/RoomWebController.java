package com.example.hotelsimpleservice.controller.web_controller;

import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomWebController {
    private final String ROOM_ATTRIBUTE = "room";

    private final RoomService roomService;

    @Autowired
    public RoomWebController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/show-options")
    public String roomPage() {
        return "pages/room-pages/show-room-options";
    }

    @GetMapping("/create")
    public String createBookingPage(Model model) {
        model.addAttribute(ROOM_ATTRIBUTE, new Room());
        return "pages/room-pages/create-room";
    }

    @PostMapping("/create")
    public String performCreatingRoom(@ModelAttribute("room") Room room) {
        roomService.createRoom(room);
        return "redirect:/pages/room-pages/successful-pages/successful-create-room";
    }

    @GetMapping("/successful-create")
    public String SuccessfulCreatePage() {
        return "pages/room-pages/successful-pages/successful-create-room";
    }

    @GetMapping("/update")
    public String updateRoomPage(Model model) {
        model.addAttribute(ROOM_ATTRIBUTE, new Room());
        return "pages/room-pages/update-room";
    }

    @PostMapping("/update")
    public String performUpdatingRoom(@ModelAttribute("room") Room room) {
        roomService.updateRoom(room, room.getRoomNumber());
        return "redirect:/pages/room-pages/successful-pages/successful-update-room";
    }

    @GetMapping("/successful-update")
    public String successfulUpdatePage() {
        return "pages/room-pages/successful-pages/successful-update-room";
    }

    @GetMapping("/search")
    public String findRoomPage(Model model) {
        model.addAttribute(ROOM_ATTRIBUTE, new Room());
        return "pages/room-pages/find-room";
    }

    @PostMapping("/search")
    public ModelAndView findRoomByParameter(
            @RequestParam(required = false) Boolean wifi,
            @RequestParam(required = false) Boolean free_parking,
            @RequestParam(required = false) Boolean conditioner,
            @RequestParam(required = false) Boolean fridge,
            @RequestParam(required = false) Boolean no_smoking_room,
            @RequestParam(required = false) Boolean breakfast,
            @RequestParam(required = false) Boolean free) {
        List<Room> rooms = roomService.findRoomByDifferentParameters(wifi, free_parking, conditioner, fridge,
                no_smoking_room, breakfast, free);
        ModelAndView model = new ModelAndView(ROOM_ATTRIBUTE);
        model.addObject("rooms", rooms);
        return model;
    }
}