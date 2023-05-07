package com.example.demo.entities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Subject {
    String name;
    int year;
    int semester;

    public Subject() {
    }

    public Subject(String name) throws IOException {
        this.name = name;

        ObjectMapper objectMapper = new ObjectMapper();
        List<Subject> subjects = objectMapper.readValue(new File("demo/src/main/resources/subjects.json"), new TypeReference<List<Subject>>() {
        });
        Subject subjectData = subjects.stream().filter(subj -> subj.name.equals(this.name)).findAny().get();
        this.year = subjectData.year;
        this.semester = subjectData.semester;
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
