package com.dev.backend_school.teachers.repositories;

import com.dev.backend_school.teachers.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmailPerso(String emailPerso);
}
