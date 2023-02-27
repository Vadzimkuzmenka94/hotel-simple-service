package com.example.hotelsimpleservice.controller.entity_controller;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {
    private final String DELETED_ACCOUNT_MESSAGE ="You have deleted your account from our service, come back soon";
    private final String GET_CUSTOMER = "Link for get customer by login";
    private final String DELETE_CUSTOMER = "Link for delete customer";
    private final CustomerService customerService;
    private final MailSender javaMailSender;

    @Autowired
    public CustomerRestController(CustomerService customerService, MailSender javaMailSender) {
        this.customerService = customerService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = customerService.findAll();
        addLinkToEntity(customers);
        return ResponseEntity.ok().body(customers);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto user) {
        customerService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(user));
    }

    @GetMapping("/{login}")
    ResponseEntity<Optional<Customer>> findByLogin(@PathVariable String login) {
        generateResponseWithLinks(customerService.findByLogin(login).get());
        return ResponseEntity.of(Optional.of(customerService.findByLogin(login)));
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Customer> deleteCustomer (@PathVariable String login) {
        customerService.delete(login);
        javaMailSender.sendEmail(customerService.findByLogin(login).get().getEmail(), "message", DELETED_ACCOUNT_MESSAGE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    public Customer generateResponseWithLinks(Customer customer) {
        customer.add(linkTo(methodOn(CustomerRestController.class).findByLogin(customer.getLogin())).withRel(GET_CUSTOMER));
        customer.add(linkTo(methodOn(CustomerRestController.class).deleteCustomer(customer.getLogin())).withRel(DELETE_CUSTOMER));
        return customer;
    }

    public List<Customer> addLinkToEntity(List<Customer> customers) {
        customers.stream()
                .peek(this::generateResponseWithLinks)
                .collect(Collectors.toList());
        return customers;
    }
}