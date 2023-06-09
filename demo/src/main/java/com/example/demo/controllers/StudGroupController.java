package com.example.demo.controllers;

import com.example.demo.entities.StudGroup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class StudGroupController {

    @GetMapping(path = "/studgroup/list")
    public ResponseEntity<?> listStudGroups() throws IOException {

        log.info("StudGroupController:  list studgroups");

        final ObjectMapper objectMapper = new ObjectMapper();
        List<StudGroup> studGroups = objectMapper.readValue(new File("src/main/resources/studgroups.json"), new TypeReference<List<StudGroup>>() {
        });
        assert studGroups.size() != 0 : "Student Groups database is empty";
        assert studGroups.stream().anyMatch(group -> group.getGroupName().equals("A1")) : "Student group A1 is missing from the database, corrupted database.";
        assert studGroups.stream().anyMatch(group -> group.getGroupName().equals("A2")) : "Student group A2 is missing from the database, corrupted database.";

        return ResponseEntity.ok(studGroups);
    }


}
