package com.dev.backend_school.classes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ClassDtoResponse {
    @NotNull(message = "l'Id est requis!")
    private Long id;
    @NotBlank(message = "Le nom de la classe est requise!")
    private String name;
    @NotBlank(message = "La description de la classe est requise!")
    private String description;
    private boolean archive;
}
