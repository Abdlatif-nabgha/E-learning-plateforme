package com.nabgha.springboot.mapper;

import com.nabgha.springboot.dto.CourseRequestDTO;
import com.nabgha.springboot.dto.CourseResponseDTO;
import com.nabgha.springboot.models.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    //    CourseRequestDTO --> Course
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tutors", ignore = true)
    @Mapping(target = "sections", ignore = true)
    Course toEntity(CourseRequestDTO courseRequestDTO);

    //   Course --> CourseResponseDTO
    CourseResponseDTO toDTO(Course course);
}
