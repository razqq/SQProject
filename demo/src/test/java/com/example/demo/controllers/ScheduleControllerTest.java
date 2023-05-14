package com.example.demo.controllers;

import com.example.demo.entities.Day;
import com.example.demo.entities.Room;
import com.example.demo.entities.StudGroup;
import com.example.demo.entities.Subject;
import com.example.demo.entities.Teacher;
import com.example.demo.entities.Timeslot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private ScheduleController scheduleController;

    @Test
    public void testAddTimeslotToSchedule() throws IOException {
        // Create test data
        Timeslot timeslot = new Timeslot();
        timeslot.setSubject(new Subject("Programare avansata"));
        timeslot.setTeacher(new Teacher("Radulescu Vlad"));
        timeslot.setRoom(new Room("C101", "Laboratory", 15));
        timeslot.setClassType("Laboratory");
        timeslot.setDay(Day.Thursday);
        timeslot.setStudGroup(new StudGroup("A1", 2, 45));
        timeslot.setStartTime(8);
        timeslot.setEndTime(10);

        // Call the method under test
        ResponseEntity<?> responseEntity = scheduleController.addTimeslotToSchedule(timeslot.getStartTime(),
                timeslot.getEndTime(),
                timeslot.getDay(),
                timeslot.getStudGroup(),
                timeslot.getSubject(),
                timeslot.getClassType(),
                timeslot.getTeacher(),
                timeslot.getRoom());

        // Assert that the response is successful
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    public void testGetSchedule() throws Exception {
        // Define expected results
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Timeslot> expectedSchedule = objectMapper.readValue(new File("src/main/resources/schedule.json"), new TypeReference<List<Timeslot>>() {
        });

        // Make the GET request to the controller endpoint
        MvcResult result = mockMvc.perform(get("/api/schedule/get"))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response body to get the actual results
        String responseBody = result.getResponse().getContentAsString();

        List<Timeslot> actualSchedule = objectMapper.readValue(responseBody, new TypeReference<List<Timeslot>>() {
        });

        // Assert that the actual results match the expected results
        assertEquals(expectedSchedule.size(), actualSchedule.size());
        for (int i = 0; i < expectedSchedule.size(); i++) {
            assertEquals(expectedSchedule.get(i).getDay(), actualSchedule.get(i).getDay());
            assertEquals(expectedSchedule.get(i).getEndTime(), actualSchedule.get(i).getEndTime());
            assertEquals(expectedSchedule.get(i).getStartTime(), actualSchedule.get(i).getStartTime());

        }

    }

}