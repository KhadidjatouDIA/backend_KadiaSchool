package com.dev.backend_school.classes.mapper;

import com.dev.backend_school.classes.dto.ClassDtoRequest;
import com.dev.backend_school.classes.dto.ClassDtoResponse;
import com.dev.backend_school.classes.entities.Classe;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface ClassMapper {
    Classe toClasse(ClassDtoRequest classDto) ;
    ClassDtoResponse toClassDtoResponse(Classe classe) ;
    List<ClassDtoResponse> toClassDtoResponseList(List<Classe> classeList) ;
    List<Classe> toclasseList(List<ClassDtoRequest> classetDtoList) ;
}
