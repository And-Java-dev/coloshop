package com.example.coloshop.repository;

import com.example.coloshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByUserId(int user_id);
    List<Product> findAllByCategoryName(String name);
    List<Product>findAllByName(String name);

}
