package com.example.coloshop.service.impl;

import com.example.coloshop.model.Product;
import com.example.coloshop.model.User;
import com.example.coloshop.repository.ProductRepository;
import com.example.coloshop.repository.UserRepository;
import com.example.coloshop.service.EmailService;
import com.example.coloshop.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final ProductRepository productRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.productRepository = productRepository;
    }

    @Override
    public void register( User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnable(false);
            user.setToken(UUID.randomUUID().toString());
            userRepository.save(user);
            String link = "http://localhost:8080/activate?token=" + user.getToken();
            emailService.sendSimpleMessage(user.getEmail(),
                    "Welcome",
                    "Congratulations! Dear "+user.getName()+" "+user.getSurname()+" have successfully register to system! \n" +
                            "You have to activate your account by this link " + link);
    }

    @Override
    public void activate(String token) {
        User byToken = userRepository.findByToken(token);
        if (byToken != null) {
            byToken.setEnable(true);
            byToken.setToken(null);
            userRepository.save(byToken);
            emailService.sendMessageWithAttachment(byToken.getEmail(), "Success", "You success registered", "D:\\003\\102CANON\\IMG_1225.JPG");
        }
    }



    @Override
    public void addProductOnBasket(User user,int prod_id) {
        List<Product> products = new ArrayList<>();
        products.add(productRepository.getOne(prod_id));
        user.setProducts(products);
        userRepository.save(user);
    }

    @Override
    public boolean isEmailExists(String email){
       return userRepository.findByEmail(email) != null;
    }


    public boolean isExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.getOne(id);
    }
}
