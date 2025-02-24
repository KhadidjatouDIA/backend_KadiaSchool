package com.dev.backend_school.teachers.controller;

import com.dev.backend_school.teachers.dto.TeacherDtoRequest;
import com.dev.backend_school.teachers.dto.TeacherDtoResponse;
import com.dev.backend_school.teachers.service.impl.TeacherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
@AllArgsConstructor
@Getter
@Setter
@CrossOrigin (value = "http://localhost:5174")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherDtoResponse> saveTeacher(@RequestBody @Valid TeacherDtoRequest teacherDto) {
        Optional<TeacherDtoResponse> teacherDtoResponse = teacherService.saveTeacher(teacherDto);
        return teacherDtoResponse.map(response -> new ResponseEntity<>(response, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDtoResponse> updateTeacher(@PathVariable("id") Long id, @RequestBody @Valid TeacherDtoRequest teacherDto) {
        Optional<TeacherDtoResponse> teacherDtoResponse = teacherService.updateTeacher(id, teacherDto);
        return teacherDtoResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDtoResponse> getTeacher(@PathVariable("id") Long id) {
        Optional<TeacherDtoResponse> teacherDtoResponse = teacherService.getTeacherById(id);
        return teacherDtoResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<TeacherDtoResponse>> allTeachers() {
        List<TeacherDtoResponse> teacherDtos = teacherService.getAllTeachers();
        return new ResponseEntity<>(teacherDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
