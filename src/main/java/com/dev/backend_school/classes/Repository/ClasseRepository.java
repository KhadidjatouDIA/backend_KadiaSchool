package com.dev.backend_school.classes.Repository;

import com.dev.backend_school.classes.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    Optional<Classe> findByName(String name);
}
