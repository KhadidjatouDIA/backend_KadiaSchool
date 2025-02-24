package com.dev.backend_school.courses.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CourseDtoResponse {
    @NotNull(message = "l'Id est requis!")
    private Long id;

    @NotBlank(message = "Le nom du cours est requis!")
    private String name;

    @NotBlank(message = "La description est requise!")
    private String description;

    @NotNull(message = "L'état d'archivage est requis!")
    private boolean archive;
}
