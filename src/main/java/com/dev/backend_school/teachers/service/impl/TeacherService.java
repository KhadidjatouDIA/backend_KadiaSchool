package com.dev.backend_school.teachers.service.impl;

import com.dev.backend_school.teachers.dto.TeacherDtoRequest;
import com.dev.backend_school.teachers.dto.TeacherDtoResponse;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    Optional<TeacherDtoResponse> saveTeacher(TeacherDtoRequest teacherDto);
    List<TeacherDtoResponse> getAllTeachers();
    Optional<TeacherDtoResponse> getTeacherById(Long id);
    void deleteTeacher(Long id);
    Optional<TeacherDtoResponse> updateTeacher(Long id, TeacherDtoRequest teacherDto);
}
