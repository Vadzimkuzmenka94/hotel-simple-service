package com.example.hotelsimpleservice.service.implementation;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.exceptions.AppException;

import com.example.hotelsimpleservice.mapper.implementation.CustomerMapper;
import com.example.hotelsimpleservice.repository.CustomerRepository;
import com.example.hotelsimpleservice.service.CustomerService;
import com.example.hotelsimpleservice.validator.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceImplementationTest {
    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private CustomerValidator customerValidator;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerMapper = Mockito.mock(CustomerMapper.class);
        customerValidator = Mockito.mock(CustomerValidator.class);
        entityManager = Mockito.mock(EntityManager.class);
        customerService = new CustomerServiceImplementation(customerRepository, customerValidator, customerMapper, entityManager);
    }

    @Test
    void save() {
        Long id = 1L;
        String login = "Login";
        String password = "6Yhjhf7iP$";
        String role = "ADMIN";
        String name = "Vadim";
        String surname = "Kuzmenko1";
        String email = "vadzim@mail.ru";
        String cardNumber = "1111-1111-1111-1111";
        CustomerDto expectedCustomerDto = CustomerDto.builder()
                .id(id)
                .login(login)
                .password(password)
                .role(role)
                .name(name)
                .surname(surname)
                .email(email)
                .cardNumber(cardNumber)
                .build();
        Mockito.when(customerService.save(new CustomerDto(1L, "Login", "6Yhjhf7iP$", "ADMIN",
                        "Vadim", "Kuzmenko", "vadzim@mail.ru", "1111-1111-1111-1111")))
                .thenReturn(expectedCustomerDto);
    }

        @Test
        void saveThrowsException () {
            Mockito.when(customerService.save(new CustomerDto(1L, "Login", "6Yhjhf7iP$", "ADMIN",
                            "Vadim", "Kuzmenko", "vadzim", "1111-1111-1111-1111")))
                    .thenThrow(AppException.class);
    }
}