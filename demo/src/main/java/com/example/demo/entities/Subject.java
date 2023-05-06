package com.example.demo.entities;

public class Subject {
    String name;
    int year;
    int semester;

    public Subject() {
    }

    public Subject(String name, int year, int semester) {
        this.name = name;
        this.year = year;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
