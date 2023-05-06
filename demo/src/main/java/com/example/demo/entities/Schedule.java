package com.example.demo.entities;

import java.util.List;

public class Schedule {
    List<Timeslot> timeslots;

    public void addTimeslot(Timeslot timeslot){
        timeslots.add(timeslot);
    }

    public void deleteTimeslot(Timeslot timeslot){
        int index = timeslots.indexOf(timeslot);
        timeslots.remove(index);
    }
}
