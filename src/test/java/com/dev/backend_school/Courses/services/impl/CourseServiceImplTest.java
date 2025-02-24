package com.dev.backend_school.Courses.services.impl;
import com.dev.backend_school.courses.services.impl.CourseServiceImpl;
import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import com.dev.backend_school.courses.dto.request.CourseDtoRequest;
import com.dev.backend_school.courses.dto.response.CourseDtoResponse;
import com.dev.backend_school.courses.entities.Course;
import com.dev.backend_school.courses.mapper.CourseMapper;
import com.dev.backend_school.courses.repository.CoursesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CoursesRepository coursesRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private MessageSource messageSource;

    @Test
    void saveCourseOK() {
        when(coursesRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(courseMapper.toCourse(any())).thenReturn(getCourse());
        when(coursesRepository.save(any())).thenReturn(getCourse());
        when(courseMapper.toCourseDtoResponse(any())).thenReturn(getCourseDtoResponse());

        Optional<CourseDtoResponse> courseDtoResponse = courseService.saveCourse(getCourseDtoRequest());
        assertTrue(courseDtoResponse.isPresent());
    }

    @Test
    void saveCourseKO() {
        when(coursesRepository.findByName(anyString())).thenReturn(Optional.of(getCourse()));
        when(messageSource.getMessage(eq("course.exists"), any(), any(Locale.class)))
                .thenReturn("The course already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> courseService.saveCourse(getCourseDtoRequest()));
        assertEquals("The course already exists", exception.getMessage());
    }

    @Test
    void getAllCourses() {
        when(coursesRepository.findAll()).thenReturn(List.of(getCourse()));
        when(courseMapper.toCourseDtoResponseList(any())).thenReturn(List.of(getCourseDtoResponse()));

        List<CourseDtoResponse> courses = courseService.getAllCourses();
        assertFalse(courses.isEmpty());
        assertEquals(1, courses.size());
    }

    @Test
    void getCourseByIdOK() {
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(getCourse()));
        when(courseMapper.toCourseDtoResponse(any())).thenReturn(getCourseDtoResponse());

        Optional<CourseDtoResponse> courseDtoResponse = courseService.getCourseById(1L);
        assertTrue(courseDtoResponse.isPresent());
        assertEquals("Mathematics", courseDtoResponse.get().getName());
    }

    @Test
    void getCourseByIdKO() {
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("course.notfound"), any(), any(Locale.class)))
                .thenReturn("Course not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> courseService.getCourseById(1L));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void deleteCourseOK() {
        when(coursesRepository.existsById(anyLong())).thenReturn(true);

        courseService.deleteCourse(1L);
        verify(coursesRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteCourseKO() {
        when(coursesRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage(eq("course.notfound"), any(), any(Locale.class)))
                .thenReturn("Course not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> courseService.deleteCourse(1L));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void updateCourseOK() {
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(getCourse()));
        when(coursesRepository.save(any())).thenReturn(getCourse());
        when(courseMapper.toCourseDtoResponse(any())).thenReturn(getCourseDtoResponse());

        Optional<CourseDtoResponse> updatedCourse = courseService.updateCourse(1L, getCourseDtoRequest());
        assertTrue(updatedCourse.isPresent());
        assertEquals("Mathematics", updatedCourse.get().getName());
    }

    @Test
    void updateCourseKO() {
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("course.notfound"), any(), any(Locale.class)))
                .thenReturn("Course not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> courseService.updateCourse(1L, getCourseDtoRequest()));
        assertEquals("Course not found", exception.getMessage());
    }

    private CourseDtoRequest getCourseDtoRequest() {
        CourseDtoRequest courseDtoRequest = new CourseDtoRequest();
        courseDtoRequest.setName("Mathematics");
        courseDtoRequest.setDescription("Basic mathematics course.");
        courseDtoRequest.setArchive(false);
        return courseDtoRequest;
    }

    private Course getCourse() {
        Course course = new Course();
        course.setName("Mathematics");
        course.setDescription("Basic mathematics course.");
        course.setArchive(false);
        return course;
    }

    private CourseDtoResponse getCourseDtoResponse() {
        CourseDtoResponse courseDtoResponse = new CourseDtoResponse();
        courseDtoResponse.setName("Mathematics");
        courseDtoResponse.setDescription("Basic mathematics course.");
        courseDtoResponse.setArchive(false);
        return courseDtoResponse;
    }
}
