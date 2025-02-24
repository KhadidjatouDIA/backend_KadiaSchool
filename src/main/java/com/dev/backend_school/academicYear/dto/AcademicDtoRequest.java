package com.dev.backend_school.academicYear.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AcademicDtoRequest {
    @NotBlank(message = "Le nom de l'année est requis!")
    private String name;
    @NotBlank(message = "La description de l'année  est requise!")
    private String description;
    private boolean archive;
}
