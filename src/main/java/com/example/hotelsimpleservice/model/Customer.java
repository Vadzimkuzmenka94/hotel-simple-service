
package com.example.hotelsimpleservice.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Component
@Table(name = "customer", schema = "public")
public class Customer extends RepresentationModel <Customer>  {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long customer_id;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String role;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column(name = "card_number")
    private String cardNumber;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Booking> bookings;

    @PrePersist
    public void prePersist() {
        this.role = "USER";
    }
}