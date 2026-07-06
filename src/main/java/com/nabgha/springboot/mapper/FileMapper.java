package com.nabgha.springboot.mapper;

import com.nabgha.springboot.dto.request.FileRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.models.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lecture", ignore = true)
    File toEntity(FileRequestDTO fileRequestDTO);

    @Mapping(target = "length", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "type", constant = "file")
    @Mapping(target = "fileType", source = "type")
    ResourceResponseDTO toDTO(File file);
}
