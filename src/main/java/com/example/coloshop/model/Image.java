package com.example.coloshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "image")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;


    @JsonIgnore
    @ManyToMany(cascade=CascadeType.ALL ,mappedBy = "images")
    private List<Product> products;
}
