
package com.example.hotelsimpleservice.service;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);

    Optional<Customer> findCustomerByLogin(String login);

    void deleteCustomer(String login);

    List<Customer> findAllCustomers();
}