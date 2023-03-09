package com.example.hotelsimpleservice.controller.entity_rest_controller;

import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v-rest/rooms")
public class RoomRestController {
    private final String GET_ROOM = "Link for get room";
    private final String UPDATE_ROOM = "Link for update room";
    private final RoomService roomService;

    @Autowired
    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    ResponseEntity<Room> findById(@PathVariable int id) {
        generateResponseWithLinks(roomService.findByRoomNumber(id));
        return ResponseEntity.of(Optional.of(roomService.findByRoomNumber(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Room>> findRoomByParameter(@RequestParam(required = false) Boolean wifi,
                                                          @RequestParam(required = false) Boolean free_parking,
                                                          @RequestParam(required = false) Boolean conditioner,
                                                          @RequestParam(required = false) Boolean fridge,
                                                          @RequestParam(required = false) Boolean no_smoking_room,
                                                          @RequestParam(required = false) Boolean breakfast,
                                                          @RequestParam(required = false) Boolean free) {
        List<Room> rooms = roomService.findRoomByDifferentParameters(wifi, free_parking, conditioner, fridge,
                no_smoking_room, breakfast, free);
        addLinkToEntity(rooms);
        return ResponseEntity.ok().body(rooms);
    }


    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(room));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Room> update(@RequestBody Room room,
                                       @PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(room, id));
    }

    public Room generateResponseWithLinks(Room room) {
        room.add(linkTo(methodOn(RoomRestController.class).findById(room.getRoomNumber())).withRel(GET_ROOM));
        room.add(linkTo(methodOn(RoomRestController.class).update(room, room.getRoomNumber())).withRel(UPDATE_ROOM));
        return room;
    }

    public List<Room> addLinkToEntity(List<Room> rooms) {
        rooms.stream()
                .peek(this::generateResponseWithLinks)
                .collect(Collectors.toList());
        return rooms;
    }
}