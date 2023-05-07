package com.example.demo.controllers;

import com.example.demo.entities.Teacher;
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
public class TeacherController {

    @GetMapping(path = "/teacher/list")
    public ResponseEntity<?> listTeachers() throws IOException {

        log.info("TeacherController:  list teachers");

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Teacher> teachers = objectMapper.readValue(new File("demo/src/main/resources/teachers.json"), new TypeReference<List<Teacher>>() {
        });

        return ResponseEntity.ok(teachers);
    }


}
