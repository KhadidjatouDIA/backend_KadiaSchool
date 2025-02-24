package com.dev.backend_school.students.services.Impl;

import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import com.dev.backend_school.students.dto.StudentDtoRequest;
import com.dev.backend_school.students.dto.StudentDtoResponse;
import com.dev.backend_school.students.entities.Student;
import com.dev.backend_school.students.mapper.StudentMapper;
import com.dev.backend_school.students.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Getter
@Setter
//@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private static final String STUDENT_NOT_FOUND = "student.notfound";
    private static final String STUDENT_EXISTS = "student.exists";

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public Optional<StudentDtoResponse> saveStudent(StudentDtoRequest studentDto) {
        if (studentRepository.findByEmailPerso(studentDto.getEmailPerso()).isPresent()) {
          throw new EntityExistsException(messageSource.getMessage(STUDENT_EXISTS, new Object[]{studentDto.getEmailPerso()}, Locale.getDefault()));
        }
        Student student = studentMapper.toStudent(studentDto);
         log.info("Saving student: {}", studentDto);
        Student studentEntity = studentRepository.save(student);
        return Optional.of(studentMapper.toStudentDtoResponse(studentEntity));
    }

    @Override
    public List<StudentDtoResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toStudentDtoResponseList(students);
    }

    @Override
    public Optional<StudentDtoResponse> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toStudentDtoResponse)
                .or(() -> {
                  throw new EntityNotFoundException(messageSource.getMessage(STUDENT_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException(messageSource.getMessage(STUDENT_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
        }
        studentRepository.deleteById(id);
      log.info("Deleted student with ID: {}", id);
    }

    @Override
    public Optional<StudentDtoResponse> updateStudent(Long id, StudentDtoRequest studentDto) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(studentDto.getFirstName());
                    student.setLastName(studentDto.getLastName());
                    student.setEmailPro(studentDto.getEmailPro());
                    student.setEmailPerso(studentDto.getEmailPerso());
                    student.setPhoneNumber(studentDto.getPhoneNumber());
                    student.setAddress(studentDto.getAddress());
//                    student.setArchive(studentDto.isArchive());
                    student.setRegistrationNo(studentDto.getRegistrationNo());

                    log.info("Updated student with ID: {}", id);
                    return studentMapper.toStudentDtoResponse(studentRepository.save(student));
                })
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(STUDENT_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }
}
