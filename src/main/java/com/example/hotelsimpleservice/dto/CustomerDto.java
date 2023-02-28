package com.example.hotelsimpleservice.dto;

import com.example.hotelsimpleservice.model.Booking;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CustomerDto extends RepresentationModel<CustomerDto> {
    private Long id;
    private String login;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String email;
    private String cardNumber;
    Set<Booking> bookings;

    public CustomerDto(Long id, String login, String password, String role, String name,
                       String surname, String email, String cardNumber) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}