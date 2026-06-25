package com.nabgha.springboot.repository;

import com.nabgha.springboot.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Integer> {
}