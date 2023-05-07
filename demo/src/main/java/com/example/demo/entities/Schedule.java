package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public static List<Timeslot> timeslots = new ArrayList<>();

    public void addTimeslot(Timeslot timeslot){
        timeslots.add(timeslot);
    }

    public void deleteTimeslot(Timeslot timeslot){
        int index = timeslots.indexOf(timeslot);
        timeslots.remove(index);
    }
}
