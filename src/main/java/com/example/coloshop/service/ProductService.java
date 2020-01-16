package com.example.coloshop.service;


import com.example.coloshop.model.Product;
import com.example.coloshop.model.Size;
import com.example.coloshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    byte[] getImage(String picUrl) throws IOException;

    ResponseEntity addProduct(int category_id, User user, MultipartFile [] multipartFile, Product product, List<Size> sizes) throws IOException;

    List<Product>findAllByName(String name);

    void delete(Product product);

    Page<Product> findByPageable(Pageable pageable);

    Product getOne(int id);

    List<Product> findAllByUserId(int user_id);

    List<Product> findAllByCategoryName(String catName);

    List<Product> findAll();

    void addProductOnBasket(User user,int prod_id);

    void save(Product product);
}
