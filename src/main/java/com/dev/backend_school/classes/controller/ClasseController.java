package com.dev.backend_school.classes.controller;

import com.dev.backend_school.classes.dto.ClassDtoRequest;
import com.dev.backend_school.classes.dto.ClassDtoResponse;
import com.dev.backend_school.classes.services.Impl.ClasseService;
import com.dev.backend_school.students.dto.StudentDtoResponse;
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
@RequestMapping("/classes")
@AllArgsConstructor
@Getter
@Setter
public class ClasseController {

    private final ClasseService classeService;

    @PostMapping
    public ResponseEntity<ClassDtoResponse> saveClasse(@RequestBody @Valid ClassDtoRequest classDto) {
        Optional<ClassDtoResponse> classDto1 = classeService.saveClasse(classDto);
        return new ResponseEntity<>(classDto1.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDtoResponse> updateClasse(@PathVariable("id") Long id, @RequestBody @Valid ClassDtoRequest classDto) {
        Optional<ClassDtoResponse> classDtoResponse = classeService.updateClasse(id, classDto);
        return classDtoResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDtoResponse> getClasse(@PathVariable("id") Long id) {
        Optional<ClassDtoResponse> classDtoResponse = classeService.getClasseById(id);
        return classDtoResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ClassDtoResponse>> allClasses() {
        List<ClassDtoResponse> classDtos = classeService.getAllClasses();
        return new ResponseEntity<>(classDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable("id") Long id) {
        classeService.deleteClasse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
