package com.example.coloshop.repository;

import com.example.coloshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    List<Image> findAllByProductsId(int id);
}
