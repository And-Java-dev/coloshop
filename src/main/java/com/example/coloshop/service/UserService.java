package com.example.coloshop.service;


import com.example.coloshop.model.User;

import java.util.List;


public interface UserService {

    void register( User user);

    void activate(String token);

    void addProductOnBasket(User user,int prod_id);

    boolean isEmailExists(String email);

    List<User> findAll();
    
    User findById(int id);

    boolean isExists(String email);

    void save(User user);

    void deleteById(int id);

    User findByEmail(String email);
}
