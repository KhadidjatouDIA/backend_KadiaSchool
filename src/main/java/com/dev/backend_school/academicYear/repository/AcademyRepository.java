package com.dev.backend_school.academicYear.repository;

import com.dev.backend_school.academicYear.entities.AcademyYear;
import com.dev.backend_school.classes.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademyRepository  extends JpaRepository<AcademyYear, Long> {
    Optional<AcademyYear> findByName(String name);
}
