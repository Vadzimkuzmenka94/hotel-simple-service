package com.example.hotelsimpleservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Table(name = "rooms", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends RepresentationModel<Room> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean wifi;
    @Column(name = "free_parking")
    private Boolean freeParking;
    private Boolean conditioner;
    private Boolean fridge;
    @Column(name = "no_smoking_room")
    @Getter
    private Boolean noSmokingRoom;
    private Boolean breakfast;
    private Double cost;
    private String comment;
    @Column(name = "number_of_beds")
    private int numberOfBeds;
    private Boolean free;
    @Column (name = "room_number")
    private int roomNumber;
}