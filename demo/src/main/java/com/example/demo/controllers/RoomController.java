package com.example.demo.controllers;

import com.example.demo.entities.Room;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class RoomController {

    @GetMapping(path = "/rooms/list")
    public ResponseEntity<?> listRooms() throws IOException {

        log.info("RoomController:  list rooms");

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Room> rooms = objectMapper.readValue(new File("src/main/resources/rooms.json"), new TypeReference<List<Room>>() {
        });
        assert rooms.size() != 0 : "Rooms database is empty";
        assert rooms.stream().anyMatch(room -> room.getName().equals("C309")) : "Room C309 is missing from the database, corrupted database.";
        assert rooms.stream().anyMatch(room -> room.getName().equals("C308")) : "Room C308 is missing from the database, corrupted database.";

        return ResponseEntity.ok(rooms);
    }


}
