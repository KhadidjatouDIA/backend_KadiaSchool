package com.dev.backend_school.courses.services.impl;

import com.dev.backend_school.courses.dto.request.CourseDtoRequest;
import com.dev.backend_school.courses.dto.response.CourseDtoResponse;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Optional<CourseDtoResponse> saveCourse(CourseDtoRequest courseDto);
    List<CourseDtoResponse> getAllCourses();
    Optional<CourseDtoResponse> getCourseById(Long id);
    void deleteCourse(Long id);
    Optional<CourseDtoResponse> updateCourse(Long id, CourseDtoRequest courseDto);
}
