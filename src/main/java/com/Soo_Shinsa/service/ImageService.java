package com.Soo_Shinsa.service;

import com.Soo_Shinsa.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadImage(MultipartFile file, String dirName);
    void deleteImage(Long imageId);
}
