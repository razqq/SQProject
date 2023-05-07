package com.example.demo.entities;

public class Timeslot {
    int startTime;
    int endTime;
    String day;
    StudGroup studGroup;
    Subject subject;
    String classType;
    Teacher teacher;
    Room room;


    public Timeslot() {
    }

    public Timeslot(int startTime, int endTime, String day, StudGroup studGroup, Subject subject,
                    String classType, Teacher teacher, Room room) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.studGroup = studGroup;
        this.subject = subject;
        this.classType = classType;
        this.teacher = teacher;
        this.room = room;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public StudGroup getStudGroup() {
        return studGroup;
    }

    public void setStudGroup(StudGroup studGroup) {
        this.studGroup = studGroup;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
