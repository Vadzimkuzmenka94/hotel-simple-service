package com.example.hotelsimpleservice.mapper.implementation;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.mapper.Mapper;
import com.example.hotelsimpleservice.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper implements Mapper<Customer, CustomerDto> {
    @Override
    public CustomerDto mapToDto(Customer entity) {
        CustomerDto customerDto = CustomerDto.builder()
                .id(entity.getCustomer_id())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .role(entity.getRole())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .cardNumber(entity.getCardNumber())
                .bookings(entity.getBookings())
                .build();

        return customerDto;
    }

    @Override
    public Customer mapToEntity(CustomerDto dto) {
        Customer customer = Customer.builder()
                .customer_id(dto.getId())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .role(dto.getRole())
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .cardNumber(dto.getCardNumber())
                .build();
        if (!(dto.getBookings() == null))
            customer.setBookings(dto.getBookings());
        return customer;
    }
}