
package com.example.hotelsimpleservice.service;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);

    Optional<Customer> readByName(String login);

    void delete (String login);

    List<Customer> findAll();

}

