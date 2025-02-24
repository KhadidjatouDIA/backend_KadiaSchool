package com.dev.backend_school.courses.services.impl;

import com.dev.backend_school.courses.dto.request.CourseDtoRequest;
import com.dev.backend_school.courses.dto.response.CourseDtoResponse;
import com.dev.backend_school.courses.entities.Course;
import com.dev.backend_school.courses.mapper.CourseMapper;
import com.dev.backend_school.courses.repository.CoursesRepository;
import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
@Service
public class CourseServiceImpl implements CourseService{
    private static final String COURSE_NOT_FOUND = "course.notfound";
    private static final String COURSE_EXISTS = "course.exists";

    private final CoursesRepository coursesRepository;
    private final CourseMapper courseMapper;
    private final MessageSource messageSource;

    @Override
    public Optional<CourseDtoResponse> saveCourse(CourseDtoRequest courseDto) {
            if (coursesRepository.findByName(courseDto.getName()).isPresent()){
            throw new EntityExistsException(messageSource.getMessage(COURSE_EXISTS, new Object[]{courseDto.getName()}, Locale.getDefault()));
        }
        Course course = courseMapper.toCourse(courseDto);
        log.info("Saving course: {}", course);
        Course courseEntity = coursesRepository.save(course);
        return Optional.of(courseMapper.toCourseDtoResponse(courseEntity));
    }

    @Override
    public List<CourseDtoResponse> getAllCourses() {
        List<Course> courses = coursesRepository.findAll();
        return courseMapper.toCourseDtoResponseList(courses);
    }

    @Override
    public Optional<CourseDtoResponse> getCourseById(Long id) {
        return coursesRepository.findById(id)
                .map(courseMapper::toCourseDtoResponse)
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(COURSE_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }

    @Override
    public void deleteCourse(Long id) {
        if (!coursesRepository.existsById(id)) {
            throw new EntityNotFoundException(messageSource.getMessage(COURSE_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
        }
        coursesRepository.deleteById(id);
        log.info("Deleted course with ID: {}", id);
    }

    @Override
    public Optional<CourseDtoResponse> updateCourse(Long id, CourseDtoRequest courseDto) {
        return coursesRepository.findById(id)
                .map(course -> {
                    course.setName(courseDto.getName());
                    course.setDescription(courseDto.getDescription());
                    course.setArchive(courseDto.isArchive());
                    log.info("Updated course with ID: {}", id);
                    return courseMapper.toCourseDtoResponse(coursesRepository.save(course));
                })
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(COURSE_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }
}
