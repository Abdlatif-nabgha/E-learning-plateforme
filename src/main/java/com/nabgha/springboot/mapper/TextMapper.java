package com.nabgha.springboot.mapper;

import com.nabgha.springboot.dto.request.TextRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.models.Resource;
import com.nabgha.springboot.models.Text;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TextMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lecture", ignore = true)
    Text toEntity(TextRequestDTO textRequestDTO);

    @Mapping(target = "length", ignore = true)
    @Mapping(target = "fileType", ignore = true)
    @Mapping(target = "type", constant = "text")
    ResourceResponseDTO toDTO(Resource resource);
}
