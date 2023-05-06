package com.example.demo.entities;

public class Room {
    String name;
    String roomType;
    int capacity;

    public Room() {
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
