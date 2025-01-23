package com.Soo_Shinsa.service;

import com.Soo_Shinsa.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image saveImage (MultipartFile file);
    void deleteImage(Long imageId);
}
