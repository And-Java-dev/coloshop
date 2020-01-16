package com.example.coloshop.service;


import com.example.coloshop.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    void addCategory(MultipartFile multipartFile,Category category) throws IOException;

}
