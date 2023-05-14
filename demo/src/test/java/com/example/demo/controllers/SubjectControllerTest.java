package com.example.demo.controllers;

import com.example.demo.entities.Subject;
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
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListSubjects() throws Exception {
        // Define expected results
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Subject> expectedSubjects = objectMapper.readValue(new File("src/main/resources/subjects.json"), new TypeReference<List<Subject>>() {
        });

        // Make the GET request to the controller endpoint
        MvcResult result = mockMvc.perform(get("/api/subjects/list"))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response body to get the actual results
        String responseBody = result.getResponse().getContentAsString();

        List<Subject> actualSubjects = objectMapper.readValue(responseBody, new TypeReference<List<Subject>>() {
        });

        // Assert that the actual results match the expected results
        assertEquals(expectedSubjects.size(), actualSubjects.size());
        for (int i = 0; i < expectedSubjects.size(); i++) {
            assertEquals(expectedSubjects.get(i).getSemester(), actualSubjects.get(i).getSemester());
            assertEquals(expectedSubjects.get(i).getName(), actualSubjects.get(i).getName());
            assertEquals(expectedSubjects.get(i).getYear(), actualSubjects.get(i).getYear());


        }
    }

}