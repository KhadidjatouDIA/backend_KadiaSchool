package com.dev.backend_school.academicYear.mapper;

import com.dev.backend_school.academicYear.dto.AcademicDtoRequest;
import com.dev.backend_school.academicYear.dto.AcademicDtoResponse;
import com.dev.backend_school.academicYear.entities.AcademyYear;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AcademyMapper {
    AcademyYear toAcademyYear(AcademicDtoRequest academyYearDto);
    AcademicDtoResponse toAcademicDtoResponse(AcademyYear academyYear);
    List<AcademicDtoResponse> toAcademicDtoResponseList(List<AcademyYear> academyYearList);
    List<AcademyYear> toAcademyYearList(List<AcademicDtoRequest> academyYearDtoList);
}
