package com.nabgha.springboot.mapper;


import com.nabgha.springboot.dto.request.LectureRequestDTO;
import com.nabgha.springboot.dto.response.LectureResponseDTO;
import com.nabgha.springboot.models.Lecture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResourceMapper.class)
public interface LectureMapper {

    // 1. LectureRequestDTO => Lecture
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "resource", ignore = true)
    Lecture toEntity(LectureRequestDTO requestDTO);


    // 2. Lecture => LectureResponseDTO
    @Mapping(target = "sectionId", source = "section.id")
    LectureResponseDTO toDTO(Lecture lecture);
}
