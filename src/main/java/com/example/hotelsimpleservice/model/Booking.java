package com.example.hotelsimpleservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "booking", schema = "public")
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startBooking;
    @Column(name = "finish_booking")
    private LocalDateTime finishBooking;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
        this.startBooking = startBooking.plusHours(12);
        this.finishBooking = startBooking.plusDays(this.duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Booking booking = (Booking) o;
        return roomNumber == booking.roomNumber && Objects.equals(name, booking.name) && Objects.equals(startBooking, booking.startBooking) && Objects.equals(finishBooking, booking.finishBooking) && Objects.equals(customer, booking.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, roomNumber, startBooking, finishBooking, customer);
    }
}