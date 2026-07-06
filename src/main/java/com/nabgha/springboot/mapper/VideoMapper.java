package com.nabgha.springboot.mapper;

import com.nabgha.springboot.dto.request.VideoRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.models.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VideoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lecture", ignore = true)
    Video toEntity(VideoRequestDTO videoRequestDTO);


    @Mapping(target = "fileType",  ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "type", constant = "video")
    ResourceResponseDTO toDTO(Video video);
}
