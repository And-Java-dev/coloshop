package com.example.coloshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType type = UserType.USER;

    @Column
    private boolean isEnable;

    @Column
    private String token;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "users")
    private List<Product> products;


}
