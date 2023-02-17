
package com.example.hotelsimpleservice.service.implementation;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import com.example.hotelsimpleservice.mapper.implementation.CustomerMapper;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.repository.CustomerRepository;
import com.example.hotelsimpleservice.service.CustomerService;
import com.example.hotelsimpleservice.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Service("customerServiceImplementation")
public class CustomerServiceImplementation implements CustomerService {

    private static final String ROLE_USER = "ROLE_USER";

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private final CustomerMapper mapper;
    private final EntityManager entityManager;

    @Autowired
    public CustomerServiceImplementation(CustomerRepository customerRepository, CustomerValidator customerValidator, CustomerMapper mapper, EntityManager entityManager) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public CustomerDto save(CustomerDto customerDto) {
        customerValidator.validate(customerDto);
        Customer customer = mapper.mapToEntity(customerDto);
        Customer customerInDb = customerRepository.save(customer);
        return mapper.mapToDto(customerInDb);
    }

    @Override
    public Optional<Customer> readByName(String login) {
        return Optional.of(customerRepository.findByLogin(login).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Transactional
    @Override
    public void delete(String login) {
        if (customerRepository.findByLogin(login).isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        entityManager.remove(customerRepository.findByLogin(login).get());
    }
}