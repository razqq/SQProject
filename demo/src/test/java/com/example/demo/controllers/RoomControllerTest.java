package com.example.demo.controllers;

import com.example.demo.entities.Room;
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
@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListRooms() throws Exception {
        // Define expected results
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Room> expectedRooms = objectMapper.readValue(new File("src/main/resources/rooms.json"), new TypeReference<List<Room>>() {
        });

        // Make the GET request to the controller endpoint
        MvcResult result = mockMvc.perform(get("/api/rooms/list"))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response body to get the actual results
        String responseBody = result.getResponse().getContentAsString();

        List<Room> actualRooms = objectMapper.readValue(responseBody, new TypeReference<List<Room>>() {
        });

        // Assert that the actual results match the expected results
        assertEquals(expectedRooms.size(), actualRooms.size());
        for (int i = 0; i < expectedRooms.size(); i++) {
            assertEquals(expectedRooms.get(i).getRoomType(), actualRooms.get(i).getRoomType());
            assertEquals(expectedRooms.get(i).getName(), actualRooms.get(i).getName());
            assertEquals(expectedRooms.get(i).getCapacity(), actualRooms.get(i).getCapacity());

        }
    }
}

