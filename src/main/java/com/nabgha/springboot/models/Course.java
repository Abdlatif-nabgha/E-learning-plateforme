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
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    private String title;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tutors_courses",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "tutor_id")}
    )
    @ToString.Exclude
    @Builder.Default
    List<Tutor> tutors = new ArrayList<>();

    ///  ======= helper methods =============
    public void addTutor(Tutor tutor) {
        this.tutors.add(tutor);
        tutor.getCourses().add(this);
    }
    public void removeTutor(Tutor tutor) {
        this.tutors.remove(tutor);
        tutor.getCourses().remove(this);
    }

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        sections.add(section);
        section.setCourse(this);
    }

    public void removeSection(Section section) {
        sections.remove(section);
        section.setCourse(null);
    }
}
