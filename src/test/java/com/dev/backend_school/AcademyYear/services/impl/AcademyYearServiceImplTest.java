package com.dev.backend_school.AcademyYear.services.impl;
import com.dev.backend_school.academicYear.dto.AcademicDtoRequest;
import com.dev.backend_school.academicYear.dto.AcademicDtoResponse;
import com.dev.backend_school.academicYear.entities.AcademyYear;
import com.dev.backend_school.academicYear.mapper.AcademyMapper;
import com.dev.backend_school.academicYear.repository.AcademyRepository;
import com.dev.backend_school.academicYear.service.AcademyServiceImpl;
import com.dev.backend_school.exception.EntityExistsException;
import com.dev.backend_school.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademyYearServiceImplTest  {

    @Mock
    private AcademyRepository academyRepository;

    @InjectMocks
    private AcademyServiceImpl academyService;

    @Mock
    private AcademyMapper academyMapper;

    @Mock
    private MessageSource messageSource;

    @Test
    void saveAcademyYearOK() {
        when(academyRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(academyMapper.toAcademyYear(any())).thenReturn(getAcademyYear());
        when(academyRepository.save(any())).thenReturn(getAcademyYear());
        when(academyMapper.toAcademicDtoResponse(any())).thenReturn(getAcademicDtoResponse());

        Optional<AcademicDtoResponse> academicDtoResponse = academyService.saveAcademyYear(getAcademicDtoRequest());
        assertTrue(academicDtoResponse.isPresent());
    }

    @Test
    void saveAcademyYearKO() {
        when(academyRepository.findByName(anyString())).thenReturn(Optional.of(getAcademyYear()));
        when(messageSource.getMessage(eq("academy.exists"), any(), any(Locale.class)))
                .thenReturn("The academy year already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> academyService.saveAcademyYear(getAcademicDtoRequest()));
        assertEquals("The academy year already exists", exception.getMessage());
    }

    @Test
    void getAllAcademyYears() {
        when(academyRepository.findAll()).thenReturn(List.of(getAcademyYear()));
        when(academyMapper.toAcademicDtoResponseList(any())).thenReturn(List.of(getAcademicDtoResponse()));

        List<AcademicDtoResponse> academyYears = academyService.getAllAcademyYears();
        assertFalse(academyYears.isEmpty());
        assertEquals(1, academyYears.size());
    }

    @Test
    void getAcademyYearByIdOK() {
        when(academyRepository.findById(anyLong())).thenReturn(Optional.of(getAcademyYear()));
        when(academyMapper.toAcademicDtoResponse(any())).thenReturn(getAcademicDtoResponse());

        Optional<AcademicDtoResponse> academicDtoResponse = academyService.getAcademyYearById(1L);
        assertTrue(academicDtoResponse.isPresent());
        assertEquals("2023-2024", academicDtoResponse.get().getName());
    }

    @Test
    void getAcademyYearByIdKO() {
        when(academyRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("academy.notfound"), any(), any(Locale.class)))
                .thenReturn("Academy year not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> academyService.getAcademyYearById(1L));
        assertEquals("Academy year not found", exception.getMessage());
    }

    @Test
    void deleteAcademyYearOK() {
        when(academyRepository.existsById(anyLong())).thenReturn(true);

        academyService.deleteAcademyYear(1L);
        verify(academyRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteAcademyYearKO() {
        when(academyRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage(eq("academy.notfound"), any(), any(Locale.class)))
                .thenReturn("Academy year not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> academyService.deleteAcademyYear(1L));
        assertEquals("Academy year not found", exception.getMessage());
    }

    @Test
    void updateAcademyYearOK() {
        when(academyRepository.findById(anyLong())).thenReturn(Optional.of(getAcademyYear()));
        when(academyRepository.save(any())).thenReturn(getAcademyYear());
        when(academyMapper.toAcademicDtoResponse(any())).thenReturn(getAcademicDtoResponse());

        Optional<AcademicDtoResponse> updatedAcademyYear = academyService.updateAcademyYear(1L, getAcademicDtoRequest());
        assertTrue(updatedAcademyYear.isPresent());
        assertEquals("2023-2024", updatedAcademyYear.get().getName());
    }

    @Test
    void updateAcademyYearKO() {
        when(academyRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("academy.notfound"), any(), any(Locale.class)))
                .thenReturn("Academy year not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> academyService.updateAcademyYear(1L, getAcademicDtoRequest()));
        assertEquals("Academy year not found", exception.getMessage());
    }

    private AcademicDtoRequest getAcademicDtoRequest() {
        AcademicDtoRequest academicDtoRequest = new AcademicDtoRequest();
        academicDtoRequest.setName("2023-2024");
        academicDtoRequest.setDescription("Academic year 2023-2024");
        academicDtoRequest.setArchive(false);
        return academicDtoRequest;
    }

    private AcademyYear getAcademyYear() {
        AcademyYear academyYear = new AcademyYear();
        academyYear.setId(1L);
        academyYear.setName("2023-2024");
        academyYear.setDescription("Academic year 2023-2024");
        academyYear.setArchive(false);
        return academyYear;
    }

    private AcademicDtoResponse getAcademicDtoResponse() {
        AcademicDtoResponse academicDtoResponse = new AcademicDtoResponse();
        academicDtoResponse.setId(1L);
        academicDtoResponse.setName("2023-2024");
        academicDtoResponse.setDescription("Academic year 2023-2024");
        academicDtoResponse.setArchive(false);
        return academicDtoResponse;
    }
}
