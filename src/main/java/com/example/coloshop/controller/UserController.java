package com.example.coloshop.controller;

import com.example.coloshop.model.Category;
import com.example.coloshop.model.Product;
import com.example.coloshop.model.Size;
import com.example.coloshop.model.User;
import com.example.coloshop.repository.ProductRepository;
import com.example.coloshop.security.CurrentUser;
import com.example.coloshop.service.CategoryService;
import com.example.coloshop.service.EmailService;
import com.example.coloshop.service.ProductService;
import com.example.coloshop.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {


    private final ProductService productService;

    private final UserService userService;

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    private final EmailService emailService;

    public UserController(ProductService productService, EmailService emailService, UserService userService, CategoryService categoryService, ProductRepository productRepository) {
        this.productService = productService;
        this.emailService = emailService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    @GetMapping("/register")
    public String register( ModelMap modelMap,@AuthenticationPrincipal CurrentUser currentUser) {
        if(currentUser != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user) {
        if (!userService.isEmailExists(user.getEmail())) {
            userService.register(user);
            return "redirect:/";
        }
        return "redirect:/register";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("token") String token) {
       userService.activate(token);
        return "redirect:/";
    }


    @PostMapping(value = "/addProduct")
    public String addProduct(@RequestParam("category_id") int category_id,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @RequestParam("image") MultipartFile multipartFile,
                             Product product,@RequestParam("size") List<Size> sizes) throws IOException {
        productService.addProduct(category_id,currentUser.getUser(), multipartFile,product,sizes);


        return "redirect:/profile";

    }

    @GetMapping("/profile")
    public String profile(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("user", currentUser.getUser());
        modelMap.addAttribute("categories", categoryService.findAll());
        List<Product> products = productService.findAllByUserId(currentUser.getUser().getId());
        modelMap.addAttribute("products",products);
        modelMap.addAttribute("size", Size.values());
        if (currentUser.getUser().getType().name().equals("ADMIN")){
            return "adminProfile";
        }
            return "userProfile";
    }



    @PostMapping(value = "/addCategory")
    public String addCategory(@RequestParam("image") MultipartFile multipartFile, Category category,
                              ModelMap modelMap,@AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("products",currentUser.getUser().getProducts());
        modelMap.addAttribute("size", Size.values());
        categoryService.addCategory(multipartFile,category);
        return "adminProfile";
    }

    @GetMapping("/basket")
    public String basket(ModelMap modelMap,@RequestParam("id") int  id,@AuthenticationPrincipal CurrentUser currentUser){
       productService.addProductOnBasket(currentUser.getUser(),id);
      List<Product> products = currentUser.getUser().getProducts();
       modelMap.addAttribute("products",products);
        if (currentUser.getUser() != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }
        return "basket";
    }

    @GetMapping("/delete")
    public String deleteProducts(ModelMap modelMap,@AuthenticationPrincipal CurrentUser currentUser, @RequestParam("id") int id) {
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("products",currentUser.getUser().getProducts());
        modelMap.addAttribute("size", Size.values());
        Product product = productRepository.getOne(id);
        productRepository.delete(product);
        return "adminProfile";
    }

    @GetMapping("/search")
    public String search(ModelMap modelMap, @RequestParam String keyword) {
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("size", Size.values());
        List<Product> products = productRepository.findAllByName(keyword);
        modelMap.addAttribute("products", products);
        return "adminProfile";
    }
}
