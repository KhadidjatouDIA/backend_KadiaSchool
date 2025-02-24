package com.dev.backend_school.students.services.impl;
import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import com.dev.backend_school.students.dto.StudentDtoRequest;
import com.dev.backend_school.students.dto.StudentDtoResponse;
import com.dev.backend_school.students.entities.Student;
import com.dev.backend_school.students.mapper.StudentMapper;
import com.dev.backend_school.students.repositories.StudentRepository;
import static org.junit.jupiter.api.Assertions.*;

import com.dev.backend_school.students.services.Impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest{

@Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private MessageSource messageSource;

    @Test
    void saveStudentOK() {
        when(studentRepository.findByEmailPerso(anyString())).thenReturn(Optional.empty());
        when(studentMapper.toStudent(any())).thenReturn(getStudentEntity());
        when(studentRepository.save(any())).thenReturn(getStudentEntity());
        when(studentMapper.toStudentDtoResponse(any())).thenReturn(getStudentDtoResponse());

        Optional<StudentDtoResponse> studentDtoResponse = studentService.saveStudent(getStudentDtoRequest());
        assertTrue(studentDtoResponse.isPresent());
    }

    @Test
    void saveStudentKO() {
        when(studentRepository.findByEmailPerso(anyString())).thenReturn(Optional.of(getStudentEntity()));
        when(messageSource.getMessage(eq("student.exists"), any(), any(Locale.class)))
                .thenReturn("The student with email = test@example.com already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () ->
                studentService.saveStudent(getStudentDtoRequest()));
        assertEquals("The student with email = test@example.com already exists", exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(getStudentEntity()));
        when(studentMapper.toStudentDtoResponseList(any())).thenReturn(List.of(getStudentDtoResponse()));

        List<StudentDtoResponse> students = studentService.getAllStudents();
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    void getStudentByIdOK() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudentEntity()));
        when(studentMapper.toStudentDtoResponse(any())).thenReturn(getStudentDtoResponse());

        Optional<StudentDtoResponse> student = studentService.getStudentById(1L);
        assertTrue(student.isPresent());
        assertEquals("test@example.com", student.get().getEmailPerso());
    }

    @Test
    void getStudentByIdKO() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("Student not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                studentService.getStudentById(1L));
        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    void deleteStudentOK() {
        when(studentRepository.existsById(anyLong())).thenReturn(true);

        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudentKO() {
        when(studentRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("Student not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                studentService.deleteStudent(1L));
        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    void updateStudentOK() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudentEntity()));
        when(studentRepository.save(any())).thenReturn(getStudentEntity());
        when(studentMapper.toStudentDtoResponse(any())).thenReturn(getStudentDtoResponse());

        Optional<StudentDtoResponse> updatedStudent = studentService.updateStudent(1L, getStudentDtoRequest());
        assertTrue(updatedStudent.isPresent());
        assertEquals("test@example.com", updatedStudent.get().getEmailPerso());
    }

    @Test
    void updateStudentKO() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("Student not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                studentService.updateStudent(1L, getStudentDtoRequest()));
        assertEquals("Student not found", exception.getMessage());
    }

    private StudentDtoRequest getStudentDtoRequest() {
        StudentDtoRequest studentDtoRequest = new StudentDtoRequest();
        studentDtoRequest.setFirstName("John");
        studentDtoRequest.setLastName("Doe");
        studentDtoRequest.setEmailPro("john.doe@company.com");
        studentDtoRequest.setEmailPerso("test@example.com");
        studentDtoRequest.setPhoneNumber("123456789");
        studentDtoRequest.setAddress("123 Main St");
        studentDtoRequest.setRegistrationNo("REG123");

        return studentDtoRequest;
    }

    private Student getStudentEntity() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmailPro("john.doe@company.com");
        student.setEmailPerso("test@example.com");
        student.setPhoneNumber("123456789");
        student.setAddress("123 Main St");
        student.setRegistrationNo("REG123");

        return student;
    }

    private StudentDtoResponse getStudentDtoResponse() {
        StudentDtoResponse studentDtoResponse = new StudentDtoResponse();
        studentDtoResponse.setFirstName("John");
        studentDtoResponse.setLastName("Doe");
        studentDtoResponse.setEmailPro("john.doe@company.com");
        studentDtoResponse.setEmailPerso("test@example.com");
        studentDtoResponse.setPhoneNumber("123456789");
        studentDtoResponse.setAddress("123 Main St");
        studentDtoResponse.setRegistrationNo("REG123");

        return studentDtoResponse;
    }
}
