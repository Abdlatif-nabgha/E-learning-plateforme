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
@DiscriminatorValue("video")
public class Video extends Resource {
    private long length;
}
