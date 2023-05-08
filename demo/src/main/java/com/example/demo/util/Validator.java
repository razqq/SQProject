package com.example.demo.util;

import com.example.demo.entities.Schedule;
import com.example.demo.entities.Timeslot;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class Validator {
    
    private static boolean validateClassTypeEqualsRoomType(Timeslot timeslot){
        return timeslot.getClassType().equals(timeslot.getRoom().getRoomType());
    }
    
    private static boolean checkTimeFrameOveralps(Timeslot existingTimeslot, Timeslot newTimeslot){
        return existingTimeslot.getStartTime() > newTimeslot.getEndTime() ||
                existingTimeslot.getEndTime() < newTimeslot.getStartTime();
    }

    private static boolean checkSameTeacher(Timeslot existingTimeslot, Timeslot newTimeslot){
        return existingTimeslot.getTeacher().getName().equals(newTimeslot.getTeacher().getName());
    }

    private static boolean checkSameRoom(Timeslot existingTimeslot, Timeslot newTimeslot){
        return existingTimeslot.getRoom().getName().equals(newTimeslot.getRoom().getName());
    }
    
    private static boolean checkSameTeacherSameRoomSameTime(Timeslot existingTimeslot, Timeslot newTimeslot){
        return checkSameRoom(existingTimeslot, newTimeslot) &&
                checkSameTeacher(existingTimeslot, newTimeslot) &&
                checkTimeFrameOveralps(existingTimeslot, newTimeslot);
    }

    public static int validateSchedule(Timeslot timeslot){

        AtomicInteger messageIndex = new AtomicInteger(0);

        if (!validateClassTypeEqualsRoomType(timeslot)){
            messageIndex.set(1);
            return messageIndex.get();
        }

        log.info("Got here");

        Schedule.timeslots.forEach(
                existingTimeslot -> {
                    if (checkSameTeacherSameRoomSameTime(existingTimeslot, timeslot)){
                        messageIndex.set(2);
                    } else if (checkSameTeacher(existingTimeslot, timeslot)) {
                        messageIndex.set(3);
                    } else if (checkSameRoom(existingTimeslot, timeslot)) {
                        messageIndex.set(4);
                    } else if (checkTimeFrameOveralps(existingTimeslot, timeslot)) {
                        messageIndex.set(5);
                    }
                }
        );

        return messageIndex.get();
    }
}
