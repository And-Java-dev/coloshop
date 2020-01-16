package com.example.coloshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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


    @ElementCollection(targetClass = Size.class, fetch = FetchType.EAGER)
    @JoinTable(name = "tblsize", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "size", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Size> Size;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> images;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "products_users",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;


    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
