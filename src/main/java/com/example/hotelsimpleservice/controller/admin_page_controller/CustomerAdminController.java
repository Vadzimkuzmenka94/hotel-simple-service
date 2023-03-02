package com.example.hotelsimpleservice.controller.admin_page_controller;

import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CustomerAdminController {
    private final String CUSTOMER_ATTRIBUTE = "customer";
    private final CustomerService customerService;

    @Autowired
    public CustomerAdminController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/delete-customer")
    public String deleteCustomerPage(Model model) {
        model.addAttribute(CUSTOMER_ATTRIBUTE, new Customer());
        return "admin-page/customer/delete-customer";
    }


    @DeleteMapping("/delete-customer")
    public String performDeletingCustomer(@ModelAttribute(CUSTOMER_ATTRIBUTE) Customer customer) {
        customerService.delete(customer.getLogin());
        return "redirect:/admin/customer/successful-pages/successful-delete-customer";
    }

    @GetMapping("/successful-delete-customer")
    public String SuccessfulRegistrationPage() {
        return "admin-page/customer/successful-pages/successful-delete-customer";
    }
}