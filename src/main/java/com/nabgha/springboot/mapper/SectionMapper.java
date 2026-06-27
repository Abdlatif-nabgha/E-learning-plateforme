package com.nabgha.springboot.mapper;

import com.nabgha.springboot.dto.SectionRequestDTO;
import com.nabgha.springboot.dto.SectionResponseDTO;
import com.nabgha.springboot.models.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "lectures", ignore = true)
    Section toEntity(SectionRequestDTO request);

    @Mapping(target = "courseId", source = "course.id")
    SectionResponseDTO toDTO(Section section);
}
