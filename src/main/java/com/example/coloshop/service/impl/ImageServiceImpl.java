package com.example.coloshop.service.impl;

import com.example.coloshop.model.Image;
import com.example.coloshop.repository.ImageRepository;
import com.example.coloshop.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    private final ImageRepository imageRepository;

    public ImageServiceImpl( ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public List<Image> findAllByProductId(int prod_id) {
        return imageRepository.findAllByProductsId(prod_id);
    }

    @Override
    public byte[] getImage(String name) {
        InputStream in = null;
        try {
            in = new FileInputStream(imageUploadDir + name);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            log.error("Image does not exists", e);
        }
        return null;
    }

}
