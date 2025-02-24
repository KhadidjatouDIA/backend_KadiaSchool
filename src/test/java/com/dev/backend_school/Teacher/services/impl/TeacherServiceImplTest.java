package com.dev.backend_school.Teacher.services.impl;
import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import com.dev.backend_school.teachers.dto.TeacherDtoRequest;
import com.dev.backend_school.teachers.dto.TeacherDtoResponse;
import com.dev.backend_school.teachers.entities.Teacher;
import com.dev.backend_school.teachers.mapper.TeacherMapper;
import com.dev.backend_school.teachers.repositories.TeacherRepository;
import com.dev.backend_school.teachers.service.impl.TeacherServiceImpl;
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
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private MessageSource messageSource;

    @Test
    void saveTeacherOK() {
        when(teacherRepository.findByEmailPerso(anyString())).thenReturn(Optional.empty());
        when(teacherMapper.toTeacher(any())).thenReturn(getTeacher());
        when(teacherRepository.save(any())).thenReturn(getTeacher());
        when(teacherMapper.toTeacherDtoResponse(any())).thenReturn(getTeacherDtoResponse());

        Optional<TeacherDtoResponse> teacherDtoResponse = teacherService.saveTeacher(getTeacherDtoRequest());
        assertTrue(teacherDtoResponse.isPresent());
    }

    @Test
    void saveTeacherKO() {
        when(teacherRepository.findByEmailPerso(anyString())).thenReturn(Optional.of(getTeacher()));
        when(messageSource.getMessage(eq("teacher.exists"), any(), any(Locale.class)))
                .thenReturn("The teacher with email already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> teacherService.saveTeacher(getTeacherDtoRequest()));
        assertEquals("The teacher with email already exists", exception.getMessage());
    }

    @Test
    void getAllTeachers() {
        when(teacherRepository.findAll()).thenReturn(List.of(getTeacher()));
        when(teacherMapper.toTeacherDtoResponseList(any())).thenReturn(List.of(getTeacherDtoResponse()));

        List<TeacherDtoResponse> teachers = teacherService.getAllTeachers();
        assertFalse(teachers.isEmpty());
        assertEquals(1, teachers.size());
    }

    @Test
    void getTeacherByIdOK() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(getTeacher()));
        when(teacherMapper.toTeacherDtoResponse(any())).thenReturn(getTeacherDtoResponse());

        Optional<TeacherDtoResponse> teacherDtoResponse = teacherService.getTeacherById(1L);
        assertTrue(teacherDtoResponse.isPresent());
        assertEquals("John", teacherDtoResponse.get().getFirstName());
    }

    @Test
    void getTeacherByIdKO() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("teacher.notfound"), any(), any(Locale.class)))
                .thenReturn("Teacher not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> teacherService.getTeacherById(1L));
        assertEquals("Teacher not found", exception.getMessage());
    }

    @Test
    void deleteTeacherOK() {
        when(teacherRepository.existsById(anyLong())).thenReturn(true);

        teacherService.deleteTeacher(1L);
        verify(teacherRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteTeacherKO() {
        when(teacherRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage(eq("teacher.notfound"), any(), any(Locale.class)))
                .thenReturn("Teacher not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> teacherService.deleteTeacher(1L));
        assertEquals("Teacher not found", exception.getMessage());
    }

    @Test
    void updateTeacherOK() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(getTeacher()));
        when(teacherRepository.save(any())).thenReturn(getTeacher());
        when(teacherMapper.toTeacherDtoResponse(any())).thenReturn(getTeacherDtoResponse());

        Optional<TeacherDtoResponse> updatedTeacher = teacherService.updateTeacher(1L, getTeacherDtoRequest());
        assertTrue(updatedTeacher.isPresent());
        assertEquals("John", updatedTeacher.get().getFirstName());
    }

    @Test
    void updateTeacherKO() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("teacher.notfound"), any(), any(Locale.class)))
                .thenReturn("Teacher not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> teacherService.updateTeacher(1L, getTeacherDtoRequest()));
        assertEquals("Teacher not found", exception.getMessage());
    }

    private TeacherDtoRequest getTeacherDtoRequest() {
        TeacherDtoRequest teacherDtoRequest = new TeacherDtoRequest();
        teacherDtoRequest.setFirstName("John");
        teacherDtoRequest.setLastName("Doe");
        teacherDtoRequest.setEmailPro("john.doe@school.com");
        teacherDtoRequest.setEmailPerso("john.doe@gmail.com");
        teacherDtoRequest.setPhoneNumber("123456789");
        teacherDtoRequest.setAddress("123 Main St");
        teacherDtoRequest.setArchive(false);
        return teacherDtoRequest;
    }

    private Teacher getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setEmailPro("john.doe@school.com");
        teacher.setEmailPerso("john.doe@gmail.com");
        teacher.setPhoneNumber("123456789");
        teacher.setAddress("123 Main St");
        teacher.setArchive(false);
        return teacher;
    }

    private TeacherDtoResponse getTeacherDtoResponse() {
        TeacherDtoResponse teacherDtoResponse = new TeacherDtoResponse();
        teacherDtoResponse.setFirstName("John");
        teacherDtoResponse.setLastName("Doe");
        teacherDtoResponse.setEmailPro("john.doe@school.com");
        teacherDtoResponse.setEmailPerso("john.doe@gmail.com");
        teacherDtoResponse.setPhoneNumber("123456789");
        teacherDtoResponse.setAddress("123 Main St");
        teacherDtoResponse.setArchive(false);
        return teacherDtoResponse;
    }
}
