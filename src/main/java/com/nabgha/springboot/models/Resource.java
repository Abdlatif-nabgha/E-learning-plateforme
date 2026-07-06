package com.nabgha.springboot.models;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "resources")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resource_type", discriminatorType = DiscriminatorType.STRING)
public class Resource extends BaseEntity {

    private String name;

    private Integer size;

    private String url;

    @OneToOne(mappedBy = "resource")
    private Lecture lecture;
}
