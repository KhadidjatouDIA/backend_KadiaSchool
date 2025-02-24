package com.dev.backend_school.teachers.service.impl;

import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import com.dev.backend_school.teachers.dto.TeacherDtoRequest;
import com.dev.backend_school.teachers.dto.TeacherDtoResponse;
import com.dev.backend_school.teachers.entities.Teacher;
import com.dev.backend_school.teachers.mapper.TeacherMapper;
import com.dev.backend_school.teachers.repositories.TeacherRepository;
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
public class TeacherServiceImpl implements TeacherService{

    private static final String TEACHER_NOT_FOUND = "teacher.notfound";
    private static final String TEACHER_EXISTS = "teacher.exists";

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final MessageSource messageSource;

    @Override
    public Optional<TeacherDtoResponse> saveTeacher(TeacherDtoRequest teacherDto) {
        if (teacherRepository.findByEmailPerso(teacherDto.getEmailPerso()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage(TEACHER_EXISTS, new Object[]{teacherDto.getEmailPerso()}, Locale.getDefault()));
        }
        Teacher teacher = teacherMapper.toTeacher(teacherDto);
        log.info("Saving teacher: {}", teacher);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return Optional.of(teacherMapper.toTeacherDtoResponse(savedTeacher));
    }

    @Override
    public List<TeacherDtoResponse> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teacherMapper.toTeacherDtoResponseList(teachers);
    }

    @Override
    public Optional<TeacherDtoResponse> getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toTeacherDtoResponse)
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(TEACHER_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new EntityNotFoundException(messageSource.getMessage(TEACHER_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
        }
        teacherRepository.deleteById(id);
        log.info("Deleted teacher with ID: {}", id);
    }

    @Override
    public Optional<TeacherDtoResponse> updateTeacher(Long id, TeacherDtoRequest teacherDto) {
        return teacherRepository.findById(id)
                .map(teacher -> {
                    teacher.setFirstName(teacherDto.getFirstName());
                    teacher.setLastName(teacherDto.getLastName());
                    teacher.setEmailPro(teacherDto.getEmailPro());
                    teacher.setEmailPerso(teacherDto.getEmailPerso());
                    teacher.setPhoneNumber(teacherDto.getPhoneNumber());
                    teacher.setAddress(teacherDto.getAddress());
                    teacher.setArchive(teacherDto.isArchive());

                    log.info("Updated teacher with ID: {}", id);
                    return teacherMapper.toTeacherDtoResponse(teacherRepository.save(teacher));
                })
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(TEACHER_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }
}
