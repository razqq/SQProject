package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.util.Validator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/api/", method = RequestMethod.GET)
public class ScheduleController {


    @CrossOrigin
    @PutMapping(path = "/schedule/add")
    public ResponseEntity<?> addTimeslotToSchedule(@RequestParam("startTime") int startTime,
                                                   @RequestParam("endTime") int endTime,
                                                   @RequestParam("day") Day day,
                                                   @RequestParam("StudGroup") StudGroup studGroup,
                                                   @RequestParam("subject") Subject subject,
                                                   @RequestParam("classType") String classType,
                                                   @RequestParam("teacher") Teacher teacher,
                                                   @RequestParam("room") Room room
    )
            throws IOException {


        log.info("ScheduleController:  submit timeslot");

        Timeslot timeslot = new Timeslot();
        timeslot.setSubject(subject);
        timeslot.setTeacher(teacher);
        timeslot.setRoom(room);
        timeslot.setClassType(classType);
        timeslot.setDay(day);
        timeslot.setStudGroup(studGroup);
        timeslot.setEndTime(endTime);
        timeslot.setStartTime(startTime);

        if (!Validator.validateSchedule(timeslot)){
            return ResponseEntity.badRequest().body("{\"message\": \"Invalid class, conflict detected\"}");
        }

        //validation before adding - e.g. check for collision
        Schedule.timeslots.add(timeslot);


        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new File("demo/src/main/resources/schedule.json"), Schedule.timeslots);


        return ResponseEntity.ok(Schedule.timeslots);
    }

    @GetMapping(path = "/schedule/get")
    public ResponseEntity<?> getSchedule() throws IOException {

        log.info("ScheduleController:  list schedule");

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Timeslot> timeslots = objectMapper.readValue(new File("demo/src/main/resources/schedule.json"), new TypeReference<List<Timeslot>>() {
        });

        return ResponseEntity.ok(timeslots);
    }
}
