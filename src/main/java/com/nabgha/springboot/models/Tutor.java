package com.nabgha.springboot.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tutors")
@NamedQuery(
        name = "Tutor.findByName",
        query = "select a from Tutor a where a.firstName = :firstName and a.lastName = :lastName"
)
public class Tutor extends BaseEntity{

    @Column(name = "f_name", length = 25)
    private String firstName;

    @Column(name = "l_name", length = 25)
    private String lastName;

    @Column(unique = true, nullable = false, length = 35)
    private String email;

    @ManyToMany(mappedBy = "tutors")
    @ToString.Exclude
    @Builder.Default
    List<Course> courses = new ArrayList<>();
}
