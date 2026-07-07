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
public class Tutor extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "tutors")
    @ToString.Exclude
    @Builder.Default
    List<Course> courses = new ArrayList<>();
}
