package com.dev.backend_school.classes.services.Impl;

import com.dev.backend_school.classes.Repository.ClasseRepository;
import com.dev.backend_school.classes.dto.ClassDtoRequest;
import com.dev.backend_school.classes.dto.ClassDtoResponse;
import com.dev.backend_school.classes.mapper.ClassMapper;
import com.dev.backend_school.classes.services.Impl.ClasseService;
import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import com.dev.backend_school.classes.entities.Classe;
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
public class ClasseServiceImpl implements ClasseService {

    private static final String CLASSE_NOT_FOUND = "classe.notfound";
    private static final String CLASSE_EXISTS = "classe.exists";

    private final ClasseRepository classeRepository;
    private final ClassMapper classMapper;
    private final MessageSource messageSource;

    @Override
    public Optional<ClassDtoResponse> saveClasse(ClassDtoRequest classeDto) {
        if (classeRepository.findByName(classeDto.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage(CLASSE_EXISTS, new Object[]{classeDto.getName()}, Locale.getDefault()));
        }
        Classe classe = classMapper.toClasse(classeDto);
        log.info("Saving class: {}", classe);
        Classe savedClasse = classeRepository.save(classe);
        return Optional.of(classMapper.toClassDtoResponse(savedClasse));
    }

    @Override
    public List<ClassDtoResponse> getAllClasses() {
        List<Classe> classes = classeRepository.findAll();
        return classMapper.toClassDtoResponseList(classes);
    }

    @Override
    public Optional<ClassDtoResponse> getClasseById(Long id) {
        return classeRepository.findById(id)
                .map(classMapper::toClassDtoResponse)
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(CLASSE_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }

    @Override
    public void deleteClasse(Long id) {
        if (!classeRepository.existsById(id)) {
            throw new EntityNotFoundException(messageSource.getMessage(CLASSE_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
        }
        classeRepository.deleteById(id);
        log.info("Deleted class with ID: {}", id);
    }

    @Override
    public Optional<ClassDtoResponse> updateClasse(Long id, ClassDtoRequest classeDto) {
        return classeRepository.findById(id)
                .map(classe -> {
                    classe.setName(classeDto.getName());
                    classe.setDescription(classeDto.getDescription());
                    classe.setArchive(classeDto.isArchive());

                    log.info("Updated class with ID: {}", id);
                    return classMapper.toClassDtoResponse(classeRepository.save(classe));
                })
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(CLASSE_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }
}
