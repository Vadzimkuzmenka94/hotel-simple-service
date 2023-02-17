
package com.example.hotelsimpleservice.security.implementation;


import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.repository.CustomerRepository;
import com.example.hotelsimpleservice.security.SecurityService;
import com.example.hotelsimpleservice.security.userDetails.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SecurityServiceImplementation implements SecurityService {
    private final CustomerRepository customerRepository;

    @Autowired
    public SecurityServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Customer> user = Optional.ofNullable(customerRepository.findByLogin(login)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return new CustomerDetails(user.get());
    }
}
