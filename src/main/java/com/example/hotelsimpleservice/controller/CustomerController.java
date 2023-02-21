package com.example.hotelsimpleservice.controller;

import com.example.hotelsimpleservice.dto.CustomerDto;
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
public class CustomerController {
    private final CustomerService customerService;
/*    private final MailSender mailSender;*/

    @Autowired
    public CustomerController(CustomerService customerService/*, MailSender mailSender*/) {
        this.customerService = customerService;
/*        this.mailSender = mailSender;*/
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
    /*    mailSender.sendEmail(user.getEmail(), "message", "Congratilations!");*/
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(user));
    }

/*    private void sendEmail(String emailTo, String emailSubject, String emailText) throws MessagingException {
        String username = "vadzimkuzmenkatest@mail.ru"; // ваш email
        String password = "OV8RnBpa6vpmdBmoanWQ"; // пароль к вашему email

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailTo));
        message.setSubject(emailSubject);
        message.setText(emailText);

        Transport.send(message);
    }*/

    @GetMapping("/{login}")
    ResponseEntity<Optional<Customer>> findByLogin(@PathVariable String login) {
        generateResponseWithLinks(customerService.readByName(login).get());
        return ResponseEntity.of(Optional.of(customerService.readByName(login)));
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Customer> deleteCustomer (@PathVariable String login) {
        customerService.delete(login);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    public Customer generateResponseWithLinks(Customer customer) {
        customer.add(linkTo(methodOn(CustomerController.class).findByLogin(customer.getLogin())).withRel("Link for get customer by login"));
        customer.add(linkTo(methodOn(CustomerController.class).deleteCustomer(customer.getLogin())).withRel("Link for delete customer"));
        return customer;
    }

    public List<Customer> addLinkToEntity(List<Customer> customers) {
        customers.stream()
                .peek(this::generateResponseWithLinks)
                .collect(Collectors.toList());
        return customers;
    }
}