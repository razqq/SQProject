package com.example.demo.controllers;

import com.example.demo.entities.StudGroup;
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
@WebMvcTest(StudGroupController.class)
public class StudGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListStudGroups() throws Exception {
        // Define expected results
        final ObjectMapper objectMapper = new ObjectMapper();
        List<StudGroup> expectedStudGroups = objectMapper.readValue(new File("src/main/resources/studgroups.json"), new TypeReference<List<StudGroup>>() {
        });

        // Make the GET request to the controller endpoint
        MvcResult result = mockMvc.perform(get("/api/studgroup/list"))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response body to get the actual results
        String responseBody = result.getResponse().getContentAsString();

        List<StudGroup> actualStudGroups = objectMapper.readValue(responseBody, new TypeReference<List<StudGroup>>() {
        });

        // Assert that the actual results match the expected results
        assertEquals(expectedStudGroups.size(), actualStudGroups.size());
        for (int i = 0; i < expectedStudGroups.size(); i++) {
            assertEquals(expectedStudGroups.get(i).getGroupName(), actualStudGroups.get(i).getGroupName());
            assertEquals(expectedStudGroups.get(i).getNrStudents(), actualStudGroups.get(i).getNrStudents());
            assertEquals(expectedStudGroups.get(i).getYear(), actualStudGroups.get(i).getYear());


        }
    }

}