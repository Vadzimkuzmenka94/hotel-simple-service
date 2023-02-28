package com.example.hotelsimpleservice.controller.general_controller;

import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/v-web/room")
public class RoomController {
    private final String ROOM_ATTRIBUTE = "room";
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/find")
    public String findRoomPage(Model model) {
        model.addAttribute(ROOM_ATTRIBUTE, new Room());
        return "general-page/find-room";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
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
