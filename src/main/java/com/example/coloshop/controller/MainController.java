package com.example.coloshop.controller;

import com.example.coloshop.model.Product;
import com.example.coloshop.security.CurrentUser;
import com.example.coloshop.service.CategoryService;
import com.example.coloshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class MainController {
    @Value("${image.upload.dir}")
    private String imageUploadDir;

    private final ProductService productService;
    private final CategoryService categoryService;

    public MainController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    public String home(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser,
                       @PageableDefault(size = 5, page = 0) Pageable pageable) {
        if (currentUser != null) {
            modelMap.addAttribute("user", currentUser.getUser());
            List<Product> products = productService.findAllByUserId(currentUser.getUser().getId());
            modelMap.addAttribute("products",products);
        }
//        modelMap.addAttribute("product",productService.getOne(id));
//        modelMap.addAttribute("images",productService.getOne(id).getImages());
        modelMap.addAttribute("products",productService.findByPageable(pageable));
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
        modelMap.addAttribute(productService.getOne(id));
        modelMap.addAttribute("images",productService.getOne(id).getImages());
        if (currentUser.getUser() != null) {
            modelMap.addAttribute("user", currentUser.getUser());
            List<Product> products = productService.findAllByUserId(currentUser.getUser().getId());
            modelMap.addAttribute("products",products);
        }
        return "single";
    }






}
