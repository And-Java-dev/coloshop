package com.example.coloshop.dto;


import com.example.coloshop.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private int id;
    private String name;
    private String surname;
    private UserType userType;
    private String email;
}
