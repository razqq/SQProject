package com.example.demo.entities;

public enum Day {

    Monday("Monday"),
    Tuesday("Tuesday");

    private final String value;

    Day(String day){
        this.value = day;
    }

    public String getValue() {
        return value;
    }
}
