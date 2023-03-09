package com.example.hotelsimpleservice.controller.web_controller;

import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.emailNotifications.Messages;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/customers")
public class CustomerWebController {
    private final String CUSTOMER_ATTRIBUTE = "customer";
    private final CustomerService customerService;
    private final MailSender mailSender;

    @Autowired
    public CustomerWebController(CustomerService customerService, MailSender mailSender) {
        this.customerService = customerService;
        this.mailSender = mailSender;
    }

    @GetMapping("/delete")
    public String deleteCustomerPage(Model model) {
        model.addAttribute(CUSTOMER_ATTRIBUTE, new Customer());
        return "pages/customer-pages/delete-customer";
    }

    @DeleteMapping("/delete")
    public String performDeletingCustomer(@ModelAttribute(CUSTOMER_ATTRIBUTE) Customer customer) {
        mailSender.sendEmail(customer.getEmail(), "message", Messages.DELETE_BOOKING_MESSAGE.getMessage());
        log.info(Messages.DELETE_BOOKING_MESSAGE.getMessage());
        customerService.deleteCustomer(customer.getLogin());
        return "redirect:/pages/customer-pages/successful-pages/successful-delete-customer";
    }

    @GetMapping("/successful-delete")
    public String successfulRegistrationPage() {
        return "pages/customer-pages/successful-pages/successful-delete-customer";
    }

    @GetMapping("/show-options")
    public String customerPage() {
        return "pages/customer-pages/show-customer-options";
    }

    @GetMapping("/show-all")
    public String showCustomers() {
        return "pages/customer-pages/show-all-customers";
    }

    @GetMapping("/search-by-id")
    public String showCustomerById() {
        return "pages/customer-pages/find-customer-by-id";
    }
}