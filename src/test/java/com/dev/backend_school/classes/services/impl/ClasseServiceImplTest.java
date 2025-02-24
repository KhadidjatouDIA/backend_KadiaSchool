package com.dev.backend_school.classes.services.impl;
import com.dev.backend_school.classes.dto.ClassDtoRequest;
import com.dev.backend_school.classes.dto.ClassDtoResponse;
import com.dev.backend_school.classes.entities.Classe;
import com.dev.backend_school.classes.mapper.ClassMapper;
import com.dev.backend_school.classes.Repository.ClasseRepository;
import com.dev.backend_school.classes.services.Impl.ClasseServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasseServiceImplTest {

    @Mock
    private ClasseRepository classeRepository;

    @InjectMocks
    private ClasseServiceImpl classeService;

    @Mock
    private ClassMapper classMapper;

    @Mock
    private MessageSource messageSource;

    @Test
    void saveClasseOK() {
        when(classeRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(classMapper.toClasse(any())).thenReturn(this.getClasse());
        when(classeRepository.save(any())).thenReturn(this.getClasse());
        when(classMapper.toClassDtoResponse(any())).thenReturn(this.getClassDtoResponse());

        Optional<ClassDtoResponse> classDtoResponse = classeService.saveClasse(this.getClassDtoRequest());
        assertTrue(classDtoResponse.isPresent());
    }

    @Test
    void saveClasseKO() {
        when(classeRepository.findByName(anyString())).thenReturn(Optional.of(this.getClasse()));
        when(messageSource.getMessage(eq("classe.exists"), any(), any(Locale.class)))
                .thenReturn("The class with name already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> classeService.saveClasse(this.getClassDtoRequest()));
        assertEquals("The class with name already exists", exception.getMessage());
    }

    @Test
    void getAllClasses() {
        when(classeRepository.findAll()).thenReturn(List.of(this.getClasse()));
        when(classMapper.toClassDtoResponseList(any())).thenReturn(List.of(this.getClassDtoResponse()));

        List<ClassDtoResponse> classes = classeService.getAllClasses();
        assertFalse(classes.isEmpty());
        assertEquals(1, classes.size());
    }

    @Test
    void getClasseByIdOK() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(classMapper.toClassDtoResponse(any())).thenReturn(getClassDtoResponse());

        Optional<ClassDtoResponse> classDtoResponse = classeService.getClasseById(1L);
        assertTrue(classDtoResponse.isPresent());
        assertEquals("Class Name", classDtoResponse.get().getName());
    }

    @Test
    void getClasseByIdKO() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("classe.notfound"), any(), any(Locale.class)))
                .thenReturn("Class not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> classeService.getClasseById(1L));
        assertEquals("Class not found", exception.getMessage());
    }

    @Test
    void deleteClasseOK() {
        when(classeRepository.existsById(anyLong())).thenReturn(true);

        classeService.deleteClasse(1L);
        verify(classeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteClasseKO() {
        when(classeRepository.existsById(anyLong())).thenReturn(false);
        when(messageSource.getMessage(eq("classe.notfound"), any(), any(Locale.class)))
                .thenReturn("Class not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> classeService.deleteClasse(1L));
        assertEquals("Class not found", exception.getMessage());
    }

    @Test
    void updateClasseOK() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(classeRepository.save(any())).thenReturn(getClasse());
        when(classMapper.toClassDtoResponse(any())).thenReturn(getClassDtoResponse());

        Optional<ClassDtoResponse> updatedClass = classeService.updateClasse(1L, getClassDtoRequest());
        assertTrue(updatedClass.isPresent());
        assertEquals("Class Name", updatedClass.get().getName());
    }

    @Test
    void updateClasseKO() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("classe.notfound"), any(), any(Locale.class)))
                .thenReturn("Class not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> classeService.updateClasse(1L, getClassDtoRequest()));
        assertEquals("Class not found", exception.getMessage());
    }

    private ClassDtoRequest getClassDtoRequest() {
        ClassDtoRequest classDtoRequest = new ClassDtoRequest();
        classDtoRequest.setName("Class Name");
        classDtoRequest.setDescription("Class Description");
        classDtoRequest.setArchive(false);
        return classDtoRequest;
    }

    private Classe getClasse() {
        Classe classe = new Classe();
        classe.setName("Class Name");
        classe.setDescription("Class Description");
        classe.setArchive(false);
        return classe;
    }

    private ClassDtoResponse getClassDtoResponse() {
        ClassDtoResponse classDtoResponse = new ClassDtoResponse();
        classDtoResponse.setName("Class Name");
        classDtoResponse.setDescription("Class Description");
        classDtoResponse.setArchive(false);
        return classDtoResponse;
    }
}
