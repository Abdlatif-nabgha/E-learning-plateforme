package com.nabgha.springboot.repository;

import com.nabgha.springboot.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}