package com.example.hotelsimpleservice.controller;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final CustomerService customerService;
    private final MailSender javaMailSender;

    @Autowired
    public AuthController(CustomerService customerService, JavaMailSender javaMailSender, MailSender javaMailSender1) {
        this.customerService = customerService;
        this.javaMailSender = javaMailSender1;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") CustomerDto customer) {
        customerService.save(customer);
        javaMailSender.sendEmail(customer.getEmail(), "message", "Congratilations!");
        return "redirect:/auth/successful-registration";
    }

    @GetMapping("/successful-registration")
    public String SuccessfulRegistrationPage() {
        return "auth/successful-registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}