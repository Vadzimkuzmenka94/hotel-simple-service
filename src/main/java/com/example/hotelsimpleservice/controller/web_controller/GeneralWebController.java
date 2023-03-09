package com.example.hotelsimpleservice.controller.web_controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class GeneralWebController {
    private final String ROLE_ADMIN = "ROLE_ADMIN";

    @GetMapping("/actions")
    public String showAllActionPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.
                getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(ROLE_ADMIN))) {
            return "pages/home-pages/show-all-actions";
        } else {
            return "pages/home-pages/start-page";
        }
    }

    @GetMapping("/user-actions")
    public String startPage() {
        return "pages/home-pages/start-page";
    }

    @GetMapping("/home")
    public String homePage() {
        return "pages/home-pages/home-page";
    }

    @GetMapping("/home/contacts")
    public String contactsPage() {
        return "pages/home-pages/contact-information";
    }

    @GetMapping("/home/galery")
    public String galeryPage() {
        return "pages/home-pages/galery-page";
    }

    @GetMapping("/home/social-networks")
    public String socialNetworksPage() {
        return "pages/home-pages/social-networks-page";
    }
}