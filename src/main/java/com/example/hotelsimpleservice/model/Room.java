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
    @Column(name = "wifi")
    private Boolean wifi;
    @Column(name = "free_parking")
    private Boolean freeParking;
    @Column(name = "conditioner")
    private Boolean conditioner;
    @Column(name = "fridge")
    private Boolean fridge;
    @Column(name = "no_smoking_room")
    @Getter
    private Boolean noSmokingRoom;
    @Column(name = "breakfast")
    private Boolean breakfast;
    @Column(name = "cost")
    private Double cost;
    @Column(name = "comment")
    private String comment;
    @Column(name = "number_of_beds")
    private int numberOfBeds;
    @Column(name = "free")
    private Boolean free;
    @Column (name = "room_number")
    private int roomNumber;
}