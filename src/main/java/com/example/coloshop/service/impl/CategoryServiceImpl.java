package com.example.coloshop.service.impl;

import com.example.coloshop.model.Category;
import com.example.coloshop.repository.CategoryRepository;
import com.example.coloshop.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(MultipartFile multipartFile,Category category) {
        String imageName = multipartFile.getOriginalFilename();
        imageName = UUID.randomUUID() + "_" + imageName;
        File file = new File(imageUploadDir, imageName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("Image does not exists",e);
        }

        category.setImagePath(imageName);
        categoryRepository.save(category);
    }
}
