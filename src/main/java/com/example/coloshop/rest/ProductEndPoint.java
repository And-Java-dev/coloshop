package com.example.coloshop.rest;


import com.example.coloshop.model.Product;
import com.example.coloshop.model.Size;
import com.example.coloshop.security.CurrentUser;
import com.example.coloshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rest/")
public class ProductEndPoint {

    private final ProductService productService;


    public ProductEndPoint(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseEntity addProduct(@RequestBody Product product) throws IOException {
//        productService.addProduct(category_id,currentUser.getUser(), multipartFile,product,sizes);
        productService.save(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("products")
    public List<Product> allProducts(){
        return productService.findAll();

    }

}
