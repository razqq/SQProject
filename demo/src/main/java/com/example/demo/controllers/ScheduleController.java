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

    private final Validator validator = new Validator();


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

        if (validator.validateSchedule(timeslot) != 0) {
            switch (validator.validateSchedule(timeslot)) {
                case 1:
                    return ResponseEntity.badRequest().body("{\"message\": \"Invalid class, the course is scheduled in the wrong room.\"," +
                            "\"errorMessage\": \"Invalid class, the course is scheduled in the wrong room.\"}");
                case 2:
                    return ResponseEntity.badRequest().body("{\"message\": \"Invalid class, the teacher has already scheduled a course here at this time.\"," +
                            "\"errorMessage\": \"Invalid class, the teacher has already scheduled a course here at this time.\"}");
                case 3:
                    return ResponseEntity.badRequest().body("{\"message\": \"Invalid class, the teacher has already a program at this time.\"," +
                            "\"errorMessage\": \"Invalid class, the teacher has already a program at this time.\"}");
                case 4:
                    return ResponseEntity.badRequest().body("{\"message\": \"Invalid class, the room is already occupied at this time.\"," +
                            "\"errorMessage\": \"Invalid class, the room is already occupied at this time.\"}");
                case 5:
                    return ResponseEntity.badRequest().body("{\"message\": \"Invalid class, it overlaps with other courses.\"," +
                            "\"errorMessage\": \"Invalid class, it overlaps with other courses.\"}");
//                return ResponseEntity.badRequest().body("{\"message\": \"Invalid class, conflict detected\"," +
//                    "\"errorMessage\": \"Invalid class, conflict detected\"}");
            }
        }

        //validation before adding - e.g. check for collision
        Schedule.timeslots.add(timeslot);


        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new File("src/main/resources/schedule.json"), Schedule.timeslots);


        return ResponseEntity.ok(Schedule.timeslots);
    }

    @GetMapping(path = "/schedule/get")
    public ResponseEntity<?> getSchedule() throws IOException {

        log.info("ScheduleController:  list schedule");

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Timeslot> timeslots = objectMapper.readValue(new File("src/main/resources/schedule.json"), new TypeReference<List<Timeslot>>() {
        });

        return ResponseEntity.ok(timeslots);
    }
}
