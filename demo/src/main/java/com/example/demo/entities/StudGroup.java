package com.example.demo.entities;

public class StudGroup {
    String groupName;
    int year;
    int nrStudents;

    public StudGroup() {
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
