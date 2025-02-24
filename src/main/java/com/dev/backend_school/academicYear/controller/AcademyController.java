package com.dev.backend_school.academicYear.controller;

import com.dev.backend_school.academicYear.dto.AcademicDtoRequest;
import com.dev.backend_school.academicYear.dto.AcademicDtoResponse;
import com.dev.backend_school.academicYear.service.AcademyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/academic-years")
@RequiredArgsConstructor
public class AcademyController {

    private final AcademyService academyService;

    @PostMapping
    public ResponseEntity<AcademicDtoResponse> saveAcademyYear(@RequestBody @Valid AcademicDtoRequest academicDtoRequest) {
        Optional<AcademicDtoResponse> response = academyService.saveAcademyYear(academicDtoRequest);
        return response.map(res -> new ResponseEntity<>(res, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicDtoResponse> updateAcademyYear(@PathVariable Long id, @RequestBody @Valid AcademicDtoRequest academicDtoRequest) {
        Optional<AcademicDtoResponse> response = academyService.updateAcademyYear(id, academicDtoRequest);
        return response.map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicDtoResponse> getAcademyYear(@PathVariable Long id) {
        Optional<AcademicDtoResponse> response = academyService.getAcademyYearById(id);
        return response.map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AcademicDtoResponse>> allAcademyYears() {
        List<AcademicDtoResponse> academyYears = academyService.getAllAcademyYears();
        return new ResponseEntity<>(academyYears, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademyYear(@PathVariable Long id) {
        academyService.deleteAcademyYear(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
