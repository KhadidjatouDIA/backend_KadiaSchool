package com.dev.backend_school.students.repositories;

import com.dev.backend_school.students.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmailPerso(String emailPerso);
}
