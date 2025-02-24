package com.dev.backend_school.teachers.mapper;

import com.dev.backend_school.teachers.dto.TeacherDtoRequest;
import com.dev.backend_school.teachers.dto.TeacherDtoResponse;
import com.dev.backend_school.teachers.entities.Teacher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    Teacher toTeacher(TeacherDtoRequest teacherDto) ;
    TeacherDtoResponse toTeacherDtoResponse(Teacher teacher) ;
    List<TeacherDtoResponse> toTeacherDtoResponseList(List<Teacher> teacherList) ;
    List<Teacher> toteacherList(List<TeacherDtoRequest> teachertDtoList) ;
}
