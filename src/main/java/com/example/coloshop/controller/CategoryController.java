package com.example.coloshop.controller;


import com.example.coloshop.model.Category;
import com.example.coloshop.model.Size;
import com.example.coloshop.security.CurrentUser;
import com.example.coloshop.service.CategoryService;
import com.example.coloshop.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(ProductService productService,CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String categories(ModelMap modelMap, @RequestParam("name") String name, @AuthenticationPrincipal CurrentUser currentUser){
        modelMap.addAttribute("products",productService.findAllByCategoryName(name));
        if (currentUser.getUser() != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }
        return "categories";
    }

    @PostMapping(value = "/addCategory")
    public String addCategory(@RequestParam("image") MultipartFile multipartFile, Category category,
                              ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("products",currentUser.getUser().getProducts());
        modelMap.addAttribute("size", Size.values());
        categoryService.addCategory(multipartFile,category);
        return "adminProfile";
    }

}
