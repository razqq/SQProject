package com.example.demo.entities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
public class StudGroup {
    String groupName;
    int year;
    int nrStudents;

    public StudGroup() {
    }

    public StudGroup(String groupName) throws IOException {
        this.groupName = groupName;
        ObjectMapper objectMapper = new ObjectMapper();
        List<StudGroup> studGroups = objectMapper.readValue(new File("src/main/resources/studgroups.json"), new TypeReference<>() {
        });
        StudGroup groupData = studGroups.stream().filter(group -> group.groupName.equals(this.groupName)).findAny().get();
        log.info(groupData.year);
        this.year = groupData.year;
        this.nrStudents = groupData.nrStudents;
    }

    public StudGroup(String groupName, int year, int nrStudents) {
        this.groupName = groupName;
        this.year = year;
        this.nrStudents = nrStudents;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getNrStudents() {
        return nrStudents;
    }

    public void setNrStudents(int nrStudents) {
        this.nrStudents = nrStudents;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
