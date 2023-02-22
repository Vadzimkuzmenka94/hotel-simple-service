package com.example.hotelsimpleservice.controller.auth_controller;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final String CUSTOMER_ATTRIBUTE = "customer";
    private final String SUCCESSFULLY_REGISTRATION = "You have successfully registered on the room booking service. Now you can book rooms on our service. Follow the link http://localhost:8080/api/auth/login and log in to your personal account";
    private final CustomerService customerService;
    private final MailSender javaMailSender;

    @Autowired
    public AuthController(CustomerService customerService, MailSender javaMailSender) {
        this.customerService = customerService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute(CUSTOMER_ATTRIBUTE, new Customer());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") CustomerDto customer) {
        customerService.save(customer);
        javaMailSender.sendEmail(customer.getEmail(), "message", SUCCESSFULLY_REGISTRATION);
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