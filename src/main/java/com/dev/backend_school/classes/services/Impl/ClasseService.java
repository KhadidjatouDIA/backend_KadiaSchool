package com.dev.backend_school.classes.services.Impl;

import com.dev.backend_school.classes.dto.ClassDtoRequest;
import com.dev.backend_school.classes.dto.ClassDtoResponse;

import java.util.List;
import java.util.Optional;

public interface ClasseService {
    Optional<ClassDtoResponse> saveClasse(ClassDtoRequest classDto);
    List<ClassDtoResponse> getAllClasses();
    Optional<ClassDtoResponse> getClasseById(Long id);
    void deleteClasse(Long id);
    Optional<ClassDtoResponse> updateClasse(Long id, ClassDtoRequest classDto);
}
