package com.example.coloshop.controller;


import com.example.coloshop.model.Product;
import com.example.coloshop.model.Size;
import com.example.coloshop.security.CurrentUser;
import com.example.coloshop.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/addProduct")
    public String addProduct(@RequestParam("category_id") int category_id,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @RequestParam("image") MultipartFile multipartFile,
                             Product product, @RequestParam("size") List<Size> sizes) throws IOException {
        productService.addProduct(category_id,currentUser.getUser(), multipartFile,product,sizes);


        return "redirect:/profile";

    }

}
