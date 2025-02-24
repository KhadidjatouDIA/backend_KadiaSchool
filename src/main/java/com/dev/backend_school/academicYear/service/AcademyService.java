package com.dev.backend_school.academicYear.service;

import com.dev.backend_school.academicYear.dto.AcademicDtoRequest;
import com.dev.backend_school.academicYear.dto.AcademicDtoResponse;

import java.util.List;
import java.util.Optional;

public interface AcademyService {
    Optional<AcademicDtoResponse> saveAcademyYear(AcademicDtoRequest academyDto);
    List<AcademicDtoResponse> getAllAcademyYears();
    Optional<AcademicDtoResponse> getAcademyYearById(Long id);
    void deleteAcademyYear(Long id);
    Optional<AcademicDtoResponse> updateAcademyYear(Long id, AcademicDtoRequest academyDto);
}
