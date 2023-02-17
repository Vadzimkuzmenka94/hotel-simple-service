package com.example.hotelsimpleservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends RepresentationModel <Booking> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private int duration;
    @Column
    private Double cost;
    @Column
    private String currency;
    @Column
    private LocalDateTime date;
    @Column(name = "room_number")
    private int roomNumber;
    @Column(name = "start_booking")
    private LocalDateTime startBooking;
    @Column(name = "finish_booking")
    private LocalDateTime finishBooking;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
        this.startBooking = startBooking.plusHours(12);
        this.finishBooking = startBooking.plusDays(this.duration);
    }
}