package com.nabgha.springboot.models;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("file")
public class File extends Resource {
    private String type;
}
