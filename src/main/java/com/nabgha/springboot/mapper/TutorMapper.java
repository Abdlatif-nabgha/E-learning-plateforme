package com.nabgha.springboot.mapper;


import com.nabgha.springboot.dto.request.TutorRequestDTO;
import com.nabgha.springboot.dto.response.TutorResponseDTO;
import com.nabgha.springboot.models.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//@Component
@Mapper(componentModel = "spring")
public interface TutorMapper {

    /***
    // 1. TutorRequestDTO -> Tutor (Entity)
    public Tutor toEntity(TutorRequestDTO dto) {
        if (dto == null) return null;

        return Tutor.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .build();
    }

    // 2. Tutor (Entity) -> TutorResponseDTO
    public TutorResponseDTO toDTO(Tutor tutor) {
        if (tutor == null) return null;

        // Records use standard constructors for instantiation
        return new TutorResponseDTO(
                tutor.getId(),
                tutor.getFirstName(),
                tutor.getLastName(),
                tutor.getEmail(),
                tutor.getCreatedAt()
        );
    }
     *****/

    // 1. TutorRequestDTO -> Tutor (Entity)
    Tutor toEntity(TutorRequestDTO dto);

    // 2. Tutor (Entity) -> TutorResponseDTO
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    TutorResponseDTO toDTO(Tutor tutor);
}
