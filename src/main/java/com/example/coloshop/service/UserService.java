package com.example.coloshop.service;


import com.example.coloshop.model.User;


public interface UserService {

    void register( User user);
    void activate(String token);
    void addProductOnBasket(User user,int prod_id);
    boolean isEmailExists(String email);
}
