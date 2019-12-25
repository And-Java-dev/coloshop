package com.example.coloshop.service;


import com.example.coloshop.model.Product;
import com.example.coloshop.model.Size;
import com.example.coloshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    byte[] getImage(String picUrl) throws IOException;

    void addProduct(int category_id, User user, MultipartFile multipartFile, Product product, List<Size> sizes) throws IOException;

    Page<Product> findByPageable(Pageable pageable);

//    List<Product> findAllByUserId(int user_id);
    List<Product> findAllByCategoryName(String catName);
    void addProductOnBasket(User user,int prod_id);
}
