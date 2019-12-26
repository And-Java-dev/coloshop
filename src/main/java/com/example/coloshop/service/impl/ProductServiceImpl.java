package com.example.coloshop.service.impl;

import com.example.coloshop.model.Category;
import com.example.coloshop.model.Product;
import com.example.coloshop.model.Size;
import com.example.coloshop.model.User;
import com.example.coloshop.repository.CategoryRepository;
import com.example.coloshop.repository.ProductRepository;
import com.example.coloshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public byte[] getImage(String picUrl) throws IOException {
        FileInputStream in = new FileInputStream(imageUploadDir + picUrl);
            return IOUtils.toByteArray(in);

    }



    @Override
    public void addProduct(int category_id, User user, MultipartFile multipartFile, Product product, List<Size> sizes) throws IOException {
        String picUrl = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        Category one = categoryRepository.getOne(category_id);
        File file = new File(imageUploadDir, picUrl);
        product.setSize(sizes);
        multipartFile.transferTo(file);
        product.setImagePath(picUrl);
        product.setCategory(one);
        product.setUser(user);
        productRepository.save(product);
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
    public void addProductOnBasket(User user,int prod_id) {
        List<User> users = new ArrayList<>();
        users.add(user);
        Product one = productRepository.getOne(prod_id);
        one.setUsers(users);
        productRepository.save(one);
        log.info("product successfully added on your basket");
    }


}
