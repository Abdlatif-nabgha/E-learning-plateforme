package com.nabgha.springboot.repository;

import com.nabgha.springboot.models.File;
import com.nabgha.springboot.models.Resource;
import com.nabgha.springboot.models.Text;
import com.nabgha.springboot.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    @Query("SELECT v FROM Video v WHERE v.id = :id")
    Optional<Video> findVideoById(Integer id);

    @Query("SELECT f FROM File f WHERE f.id = :id")
    Optional<File> findFileById(Integer id);

    @Query("SELECT t FROM Text t WHERE t.id = :id")
    Optional<Text> findTextById(Integer id);
}