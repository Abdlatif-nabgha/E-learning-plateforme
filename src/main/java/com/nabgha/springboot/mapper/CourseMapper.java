package com.nabgha.springboot.mapper;

import com.nabgha.springboot.dto.CourseRequestDTO;
import com.nabgha.springboot.dto.CourseResponseDTO;
import com.nabgha.springboot.models.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target ="tutors", ignore = true)
    Course toEntity(CourseRequestDTO courseRequestDTO);

    CourseResponseDTO toDTO(Course course);
}
