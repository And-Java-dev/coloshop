package com.example.coloshop.rest;

import com.example.coloshop.dto.AuthenticationRequest;
import com.example.coloshop.dto.AuthenticationResponse;
import com.example.coloshop.dto.UserDto;
import com.example.coloshop.exception.UserNotFoundException;
import com.example.coloshop.model.User;
import com.example.coloshop.security.JwtTokenUtil;
import com.example.coloshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserEndpoint(UserService userService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody AuthenticationRequest authenticationRequest) {
        User user = null;
        user = userService.findByEmail(authenticationRequest.getEmail());
        if (passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            String token = jwtTokenUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(token)
                    .userDto(UserDto.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .surname(user.getSurname())
                            .email(user.getEmail())
                            .userType(user.getType())
                            .build())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PutMapping("/addImage/{userId}")
    public ResponseEntity addImage(@PathVariable("userId") int userId, @RequestParam(value = "file") MultipartFile file) {
        try {
            User byId = userService.findById(userId);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File uploadFile = new File("D:\\Java\\coloshop\\imageUploadDir\\", fileName);
            file.transferTo(uploadFile);
            //user.setImage(filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity user(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody User user) {
        if (userService.isExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.ok("user was success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id , @AuthenticationPrincipal UserDetails us) {
        System.out.println("user whit" + us.getUsername() + " trying delete user by id " + id);
        userService.findById(id);
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
