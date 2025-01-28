package com.Soo_Shinsa.image;

import com.Soo_Shinsa.constant.TargetType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadImage(MultipartFile file, TargetType targetType, Long targetId);
    void deleteImage(String imageUrl);
    Image updateImage(MultipartFile newFile, String oldImageUrl, TargetType targetType);
}
