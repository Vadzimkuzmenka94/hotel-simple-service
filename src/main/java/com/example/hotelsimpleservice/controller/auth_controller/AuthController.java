package com.example.hotelsimpleservice.controller.auth_controller;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.emailNotifications.Messages;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final String CUSTOMER_ATTRIBUTE = "customer";
    private final CustomerService customerService;
    private final MailSender mailSender;

    @Autowired
    public AuthController(CustomerService customerService, MailSender mailSender) {
        this.customerService = customerService;
        this.mailSender = mailSender;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute(CUSTOMER_ATTRIBUTE, new Customer());
        return "pages/auth-pages/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") CustomerDto customer) {
        customerService.createCustomer(customer);
        mailSender.sendEmail(customer.getEmail(), "message", Messages.CREATE_BOOKING_MESSAGE.getMessage());
        log.info(Messages.CREATE_BOOKING_MESSAGE.getMessage());
        return "redirect:/auth/successful-registration";
    }

    @GetMapping("/successful-registration")
    public String SuccessfulRegistrationPage() {
        return "pages/auth-pages/successful-registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "pages/auth-pages/login";
    }
}