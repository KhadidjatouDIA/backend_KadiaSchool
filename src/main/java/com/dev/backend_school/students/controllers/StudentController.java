package com.dev.backend_school.students.controllers;

import com.dev.backend_school.students.dto.StudentDtoRequest;
import com.dev.backend_school.students.dto.StudentDtoResponse;
import com.dev.backend_school.students.services.Impl.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Getter
@Setter

@CrossOrigin (value = "http://localhost:5174")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDtoResponse> saveStudent(@RequestBody @Valid StudentDtoRequest students) {
        Optional<StudentDtoResponse> studentDto1 = studentService.saveStudent(students);
        return new ResponseEntity<>(studentDto1.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDtoResponse> updateStudent(@PathVariable("id") Long id, @RequestBody @Valid StudentDtoRequest studentDto) {
        Optional<StudentDtoResponse> studentDto1 = studentService.updateStudent(id, studentDto);
        return studentDto1.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDtoResponse> getStudent(@PathVariable("id") Long id) {
        Optional<StudentDtoResponse> studentDto1 = studentService.getStudentById(id);
        return studentDto1.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<StudentDtoResponse>> allStudents() {
        List<StudentDtoResponse> studentDtos = studentService.getAllStudents();
        return new ResponseEntity<>(studentDtos, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
