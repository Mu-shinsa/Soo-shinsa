package com.Soo_Shinsa.utils;

import com.Soo_Shinsa.constant.FileConst;
import com.Soo_Shinsa.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class ImageUtils {
    private static String path = FileConst.IMAGE_PATH;

    public Image saveImage(MultipartFile file) {
        Image image = new Image(file.getOriginalFilename(), path);

        String imagePath = getImagePath(image);
        String saveImagePath = imagePath + image.getName() + "." + image.getExtension();

        Path savePath = Paths.get(saveImagePath);
        try {
            file.transferTo(savePath);
        } catch (Exception e) {
            throw new RuntimeException("이미지 저장 실패");
        }

        return image;
    }

    private String getImagePath(Image image) {
        File file = new File(image.getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        return image.getPath();
    }
}
