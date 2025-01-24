package com.Soo_Shinsa.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadImage(MultipartFile file, String dirName);
    void deleteImage(String imageUrl);
    Image updateImage(MultipartFile newFile, String oldImageUrl, String dirName);
}
