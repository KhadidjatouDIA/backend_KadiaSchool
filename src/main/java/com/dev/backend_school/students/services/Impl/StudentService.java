package com.dev.backend_school.students.services.Impl;

import com.dev.backend_school.students.dto.StudentDtoRequest;
import com.dev.backend_school.students.dto.StudentDtoResponse;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<StudentDtoResponse> saveStudent(StudentDtoRequest studentDto);
    List<StudentDtoResponse> getAllStudents();
    Optional<StudentDtoResponse> getStudentById(Long id);
    void deleteStudent(Long id);
    Optional<StudentDtoResponse> updateStudent(Long id, StudentDtoRequest studentDto);
}


