package com.dev.syf_demo.repository;


import com.dev.syf_demo.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Images, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Images WHERE id = :id")
    void deleteImagesById(Integer id);
}
