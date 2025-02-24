package com.dev.backend_school.students.mapper;

import com.dev.backend_school.students.dto.StudentDtoRequest;
import com.dev.backend_school.students.dto.StudentDtoResponse;
import com.dev.backend_school.students.entities.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Student toStudent(StudentDtoRequest studentDto) ;
    StudentDtoResponse toStudentDtoResponse(Student student) ;
    List<StudentDtoResponse> toStudentDtoResponseList(List<Student> studentList) ;
    List<Student> tostudentList(List<StudentDtoRequest> productDtoList) ;
}
