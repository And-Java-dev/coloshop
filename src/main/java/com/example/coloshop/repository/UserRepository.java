package com.example.coloshop.repository;

import com.example.coloshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
//    User findByEmail(String username);
    Optional<User> findByEmail(String email);
    User findByToken(String token);
}
