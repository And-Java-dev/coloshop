package com.example.coloshop.security;

import com.example.coloshop.model.User;
import com.example.coloshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail == null) {
            throw new UsernameNotFoundException("User with " + username + " username does not exists!");
        }

        return new CurrentUser(byEmail.get());
    }
}
