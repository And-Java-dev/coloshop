package com.example.coloshop.repository;

import com.example.coloshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String username);

    User findByToken(String token);
}
