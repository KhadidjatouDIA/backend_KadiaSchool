package com.dev.backend_school.academicYear.service;

import com.dev.backend_school.academicYear.dto.AcademicDtoRequest;
import com.dev.backend_school.academicYear.dto.AcademicDtoResponse;
import com.dev.backend_school.academicYear.entities.AcademyYear;
import com.dev.backend_school.academicYear.mapper.AcademyMapper;
import com.dev.backend_school.academicYear.repository.AcademyRepository;
import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
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
public class AcademyServiceImpl implements AcademyService {

    private static final String ACADEMY_NOT_FOUND = "academy.notfound";
    private static final String ACADEMY_EXISTS = "academy.exists";

    private final AcademyRepository academyRepository;
    private final AcademyMapper academyMapper;
    private final MessageSource messageSource;

    @Override
    public Optional<AcademicDtoResponse> saveAcademyYear(AcademicDtoRequest academyDto) {
        if (academyRepository.findByName(academyDto.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage(ACADEMY_EXISTS, new Object[]{academyDto.getName()}, Locale.getDefault()));
        }
        AcademyYear academyYear = academyMapper.toAcademyYear(academyDto);
        log.info("Saving academy year: {}", academyYear);
        AcademyYear savedAcademyYear = academyRepository.save(academyYear);
        return Optional.of(academyMapper.toAcademicDtoResponse(savedAcademyYear));
    }

    @Override
    public List<AcademicDtoResponse> getAllAcademyYears() {
        List<AcademyYear> academyYears = academyRepository.findAll();
        return academyMapper.toAcademicDtoResponseList(academyYears);
    }

    @Override
    public Optional<AcademicDtoResponse> getAcademyYearById(Long id) {
        return academyRepository.findById(id)
                .map(academyMapper::toAcademicDtoResponse)
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(ACADEMY_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }

    @Override
    public void deleteAcademyYear(Long id) {
        if (!academyRepository.existsById(id)) {
            throw new EntityNotFoundException(messageSource.getMessage(ACADEMY_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
        }
        academyRepository.deleteById(id);
        log.info("Deleted academy year with ID: {}", id);
    }

    @Override
    public Optional<AcademicDtoResponse> updateAcademyYear(Long id, AcademicDtoRequest academyDto) {
        return academyRepository.findById(id)
                .map(academyYear -> {
                    academyYear.setName(academyDto.getName());
                    academyYear.setDescription(academyDto.getDescription());
                    academyYear.setArchive(academyDto.isArchive());

                    log.info("Updated academy year with ID: {}", id);
                    return academyMapper.toAcademicDtoResponse(academyRepository.save(academyYear));
                })
                .or(() -> {
                    throw new EntityNotFoundException(messageSource.getMessage(ACADEMY_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
                });
    }
}
