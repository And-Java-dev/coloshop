package com.example.coloshop.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double price;

    @Column
    private int count;

    @ElementCollection(targetClass = Size.class,fetch = FetchType.EAGER)
    @JoinTable(name = "tblsize", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "size", nullable = false)
    @Enumerated(EnumType.STRING)
    List<Size> Size;

    @Column
    private String  imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;



    @ManyToMany
    @JoinTable(
            name = "products_users",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List <User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
