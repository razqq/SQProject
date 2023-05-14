package com.example.demo.controllers;

import com.example.demo.entities.Teacher;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListTeachers() throws Exception {
        // Define expected results
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Teacher> expectedTeachers = objectMapper.readValue(new File("src/main/resources/teachers.json"), new TypeReference<List<Teacher>>() {
        });

        // Make the GET request to the controller endpoint
        MvcResult result = mockMvc.perform(get("/api/teacher/list"))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response body to get the actual results
        String responseBody = result.getResponse().getContentAsString();

        List<Teacher> actualTeachers = objectMapper.readValue(responseBody, new TypeReference<List<Teacher>>() {
        });
        
        // Assert that the actual results match the expected results
        assertEquals(expectedTeachers.size(), actualTeachers.size());
        for (int i = 0; i < expectedTeachers.size(); i++) {
            assertEquals(expectedTeachers.get(i).getName(), actualTeachers.get(i).getName());
        }
    }

}