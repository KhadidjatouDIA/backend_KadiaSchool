package com.dev.backend_school.courses.controller;
import com.dev.backend_school.courses.dto.request.CourseDtoRequest;
import com.dev.backend_school.courses.dto.response.CourseDtoResponse;
import com.dev.backend_school.courses.services.impl.CourseService;
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
@RequestMapping("/courses")
@AllArgsConstructor
@Getter
@Setter
@CrossOrigin (value = "http://localhost:5174")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDtoResponse> saveCourse(@RequestBody @Valid CourseDtoRequest courseDto) {
        Optional<CourseDtoResponse> courseDtoResponse = courseService.saveCourse(courseDto);
        return new ResponseEntity<>(courseDtoResponse.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDtoResponse> updateCourse(@PathVariable("id") Long id, @RequestBody @Valid CourseDtoRequest courseDto) {
        Optional<CourseDtoResponse> courseDtoResponse = courseService.updateCourse(id, courseDto);
        return courseDtoResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDtoResponse> getCourse(@PathVariable("id") Long id) {
        Optional<CourseDtoResponse> courseDtoResponse = courseService.getCourseById(id);
        return courseDtoResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<CourseDtoResponse>> allCourses() {
        List<CourseDtoResponse> courseDtos = courseService.getAllCourses();
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
