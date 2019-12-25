package com.example.coloshop.controller;

import com.example.coloshop.repository.ProductRepository;
import com.example.coloshop.security.CurrentUser;
import com.example.coloshop.service.CategoryService;
import com.example.coloshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@Slf4j
public class MainController {
    @Value("${image.upload.dir}")
    private String imageUploadDir;

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public MainController(ProductService productService, ProductRepository productRepository, CategoryService categoryService) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    public String home(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }
        modelMap.addAttribute("products",productRepository.findAll());
        modelMap.addAttribute("categories",categoryService.findAll());
        log.info("Home page was opened.");
        return "index";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picUrl") String picUrl) throws IOException {
        return productService.getImage(picUrl);
    }


    @GetMapping("/single")
    public String single(ModelMap modelMap,@RequestParam("id") int  id,@AuthenticationPrincipal CurrentUser currentUser){
        modelMap.addAttribute(productRepository.getOne(id));
        if (currentUser.getUser() != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }
        return "single";
    }



    @GetMapping("/categories")
    public String categories(ModelMap modelMap,@RequestParam("name") String name,@AuthenticationPrincipal CurrentUser currentUser){
        modelMap.addAttribute("products",productRepository.findAllByCategoryName(name));
        if (currentUser.getUser() != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }
        return "categories";
    }


}
