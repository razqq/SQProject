package com.example.demo.controllers;

import com.example.demo.entities.Subject;
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
public class SubjectController {


    @GetMapping(path = "/subjects/list")
    public ResponseEntity<?> listSubjects() throws IOException {

        log.info("SubjectController:  list subjects");

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Subject> subjects = objectMapper.readValue(new File("src/main/resources/subjects.json"), new TypeReference<List<Subject>>() {
        });
        assert subjects.size() != 0 : "Subjects database is empty";
        assert subjects.stream().anyMatch(subject -> subject.getName().equals("Programare avansata")) : "Subject Programare avansata is missing from the database, corrupted database.";
        assert subjects.stream().anyMatch(subject -> subject.getName().equals("Sisteme Embedded")) : "Subject Sisteme Embedded is missing from the database, corrupted database.";

        return ResponseEntity.ok(subjects);
    }


}

