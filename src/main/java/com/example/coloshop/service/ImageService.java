package com.example.coloshop.service;

import com.example.coloshop.model.Image;

import java.util.List;

public interface ImageService {

    List<Image> findAllByProductId(int prod_id);

    byte[] getImage(String name);
}
