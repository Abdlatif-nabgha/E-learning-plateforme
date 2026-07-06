package com.nabgha.springboot.mapper;

import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.models.File;
import com.nabgha.springboot.models.Resource;
import com.nabgha.springboot.models.Text;
import com.nabgha.springboot.models.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceMapper {

    private final VideoMapper videoMapper;
    private final FileMapper fileMapper;
    private final TextMapper textMapper;

    public ResourceResponseDTO toDTO(Resource resource) {
        if (resource == null) {
            return null;
        }
        return switch (resource) {
            case Video video -> videoMapper.toDTO(video);
            case File file -> fileMapper.toDTO(file);
            case Text text -> textMapper.toDTO(text);
            default -> throw new IllegalArgumentException("Resource not found: " + resource);
        };
    }
}
