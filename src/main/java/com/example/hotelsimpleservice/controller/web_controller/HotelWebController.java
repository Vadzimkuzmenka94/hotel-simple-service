package com.example.hotelsimpleservice.controller.web_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotels")
public class HotelWebController {

    @GetMapping()
    public String showHotels() {
        return "pages/show-all-hotels";
    }
}