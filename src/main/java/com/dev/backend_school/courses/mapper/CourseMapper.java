package com.dev.backend_school.courses.mapper;

import com.dev.backend_school.courses.dto.request.CourseDtoRequest;
import com.dev.backend_school.courses.dto.response.CourseDtoResponse;
import com.dev.backend_school.courses.entities.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    Course toCourse(CourseDtoRequest courseDto) ;
    CourseDtoResponse toCourseDtoResponse(Course course) ;
    List<CourseDtoResponse> toCourseDtoResponseList(List<Course> courseList) ;
    List<Course> tocourseList(List<CourseDtoRequest> courseDtoResponseList) ;
}
