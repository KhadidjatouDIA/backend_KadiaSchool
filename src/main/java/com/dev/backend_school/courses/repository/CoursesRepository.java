package com.dev.backend_school.courses.repository;

import com.dev.backend_school.courses.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursesRepository  extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);
}
