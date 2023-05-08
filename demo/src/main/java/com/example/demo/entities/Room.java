package com.example.demo.entities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Room {
    String name;
    String roomType;
    int capacity;

    public Room() {
    }

    public Room(String name) throws IOException {
        this.name = name;
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Room> rooms = objectMapper.readValue(new File("src/main/resources/rooms.json"), new TypeReference<List<Room>>() {
        });
        Room roomData = rooms.stream().filter(room -> room.name.equals(this.name)).findAny().get();
        this.capacity = roomData.capacity;
        this.roomType = roomData.roomType;
    }

    public Room(String name, String roomType, int capacity) {
        this.name = name;
        this.roomType = roomType;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
