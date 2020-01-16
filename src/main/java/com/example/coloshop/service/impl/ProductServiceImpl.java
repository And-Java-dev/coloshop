package com.example.coloshop.service.impl;

import com.example.coloshop.model.*;
import com.example.coloshop.repository.CategoryRepository;
import com.example.coloshop.repository.ImageRepository;
import com.example.coloshop.repository.ProductRepository;
import com.example.coloshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Value("${image.upload.dir}")
    private String imageUploadDir;


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;


    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public byte[] getImage(String picUrl) throws IOException {
        FileInputStream in = new FileInputStream(imageUploadDir + picUrl);
            return IOUtils.toByteArray(in);

    }




    @Override
    public ResponseEntity addProduct(int category_id, User user, MultipartFile [] multipartFile, Product product, List<Size> sizes) throws IOException {
        Image image = null;
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            String picUrl = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File file1 = new File(imageUploadDir, picUrl);
            image =  new Image();
            image.setName(picUrl);
            file.transferTo(file1);
            images.add(image);
            imageRepository.save(image);
        }
        Category one = categoryRepository.getOne(category_id);
        product.setSize(sizes);
        product.setImages(images);
        product.setCategory(one);
        product.setUser(user);
        productRepository.save(product);
        return null;
    }

    @Override
    public List<Product> findAllByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public  void delete(Product product){
        productRepository.delete(product);
    }

    @Override
    public Page<Product> findByPageable(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getOne(int id) {
        return productRepository.getOne(id);
    }

    @Override
    public List<Product> findAllByUserId(int user_id) {
        return productRepository.findAllByUserId(user_id);
    }


    @Override
    public List<Product> findAllByCategoryName(String catName) {
        return productRepository.findAllByCategoryName(catName);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void addProductOnBasket(User user,int prod_id) {
        List<User> users = new ArrayList<>();
        users.add(user);
        Product one = productRepository.getOne(prod_id);
        one.setUsers(users);
        productRepository.save(one);
        log.info("product successfully added on your basket");
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }


}
