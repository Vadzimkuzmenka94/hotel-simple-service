
package com.example.hotelsimpleservice.service.implementation;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.emailNotifications.Messages;
import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import com.example.hotelsimpleservice.mapper.implementation.CustomerMapper;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.repository.CustomerRepository;
import com.example.hotelsimpleservice.service.CustomerService;
import com.example.hotelsimpleservice.validator.CustomerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("customerServiceImplementation")
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private final CustomerMapper mapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public CustomerServiceImplementation(CustomerRepository customerRepository, CustomerValidator customerValidator,
                                         CustomerMapper mapper, EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional
    @Override
    public CustomerDto save(CustomerDto customerDto) {
        customerValidator.validate(customerDto);
        customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        Customer customer = mapper.mapToEntity(customerDto);
        Customer customerInDb = customerRepository.save(customer);
        return mapper.mapToDto(customerInDb);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    @Override
    public Optional<Customer> findByLogin(String login) {
        if (!isAdmin()) {
            log.info("user role is ROLE_USER");
            login = getNameFromAuthentication();
        }
        return Optional.of(customerRepository.findByLogin(login).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    /**
     * Method for delete customers, but is user not administrator, he can delete only yourself
     * @param login
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Transactional
    @Override
    public void delete(String login) {
        if (!isAdmin()) {
            log.info("user role is ROLE_USER");
            login = getNameFromAuthentication();
        }
        if (customerRepository.findByLogin(login).isEmpty()) {
            log.error("user with login= " + login + " not found");
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        entityManager.remove(customerRepository.findByLogin(login).get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Method for check user role
     * @return true / false
     */
    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }

    private String getNameFromAuthentication () {
     return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}