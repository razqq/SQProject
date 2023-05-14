package com.example.demo.util;

import com.example.demo.entities.Schedule;
import com.example.demo.entities.Timeslot;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class Validator {
    
    private boolean validateClassTypeEqualsRoomType(Timeslot timeslot){
        return timeslot.getClassType().equals(timeslot.getRoom().getRoomType());
    }
    
    private boolean checkTimeFrameOveralps(Timeslot existingTimeslot, Timeslot newTimeslot){
        return (existingTimeslot.getStartTime() > newTimeslot.getEndTime() ||
                existingTimeslot.getEndTime() < newTimeslot.getStartTime()) &&
                existingTimeslot.getDay().getValue().equals(newTimeslot.getDay().getValue());
    }

    private boolean checkSameTimeFrame(Timeslot existingTimeslot, Timeslot newTimeslot){
        return existingTimeslot.getStartTime() == newTimeslot.getEndTime() &&
                existingTimeslot.getEndTime() == newTimeslot.getStartTime() &&
                existingTimeslot.getDay().getValue().equals(newTimeslot.getDay().getValue());
    }

    private boolean checkSameTeacher(Timeslot existingTimeslot, Timeslot newTimeslot){
        return existingTimeslot.getTeacher().getName().equals(newTimeslot.getTeacher().getName());
    }

    private boolean checkSameRoom(Timeslot existingTimeslot, Timeslot newTimeslot){
        return existingTimeslot.getRoom().getName().equals(newTimeslot.getRoom().getName());
    }
    
    private boolean checkSameTeacherSameRoomSameTime(Timeslot existingTimeslot, Timeslot newTimeslot){
        return checkSameRoom(existingTimeslot, newTimeslot) &&
                checkSameTeacher(existingTimeslot, newTimeslot) &&
                checkSameTimeFrame(existingTimeslot, newTimeslot);
    }

    public int validateSchedule(Timeslot timeslot){

        AtomicInteger messageIndex = new AtomicInteger(0);
        AtomicBoolean errorFound = new AtomicBoolean(false);

        if (!validateClassTypeEqualsRoomType(timeslot)){
            messageIndex.set(1);
            return messageIndex.get();
        }

        Schedule.timeslots.forEach(
                existingTimeslot -> {
                    if (checkSameTeacherSameRoomSameTime(existingTimeslot, timeslot) && !errorFound.get()){
                        messageIndex.set(2);
                        errorFound.set(true);
                    } else if (checkSameTeacher(existingTimeslot, timeslot) && !errorFound.get()) {
                        messageIndex.set(3);
                        errorFound.set(true);
                    } else if (checkSameRoom(existingTimeslot, timeslot) && !errorFound.get()) {
                        messageIndex.set(4);
                        errorFound.set(true);
                    } else if (checkTimeFrameOveralps(existingTimeslot, timeslot) && !errorFound.get()) {
                        messageIndex.set(5);
                        errorFound.set(true);
                    }
                }
        );

        return messageIndex.get();
    }
}
