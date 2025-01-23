package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.FileConst;
import com.Soo_Shinsa.model.Image;
import com.Soo_Shinsa.repository.ImageRepository;
import com.Soo_Shinsa.service.ImageService;
import com.Soo_Shinsa.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB

    private final ImageRepository imageRepository;
    private final ImageUtils imageUtils;

    @Transactional
    @Override
    public Image saveImage (MultipartFile file) {
        validateFile(file, FileConst.IMAGE_EXTENSIONS, MAX_IMAGE_SIZE);
        Image image = imageUtils.saveImage(file);
        return imageRepository.save(image);
    }

    @Transactional
    @Override
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("이미지가 존재하지 않습니다."));

        deletePhysicalFile(image);

        imageRepository.delete(image);
    }

    private static void deletePhysicalFile(Image image) {
        String filePath = Paths.get(image.getPath(), image.getName() + "." + image.getExtension()).toString();
        File file = new File(filePath);
        if (file.exists() && file.delete()) {
            log.info("파일이 삭제되었습니다.", filePath);
        } else {
            log.error("파일이 삭제되지 않았습니다.", filePath);
        }
    }

    private void validateFile (MultipartFile file, Iterable<String> allowedExtensions, long maxSize) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        // 파일 이름 검증
        String originName = file.getOriginalFilename();
        if (originName == null || originName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 존재하지 않습니다.");
        }

        // 파일 확장자 검증
        String extension = getExtension(originName);
        if (!isExtensionAllowed(extension, allowedExtensions)) {
            throw new IllegalArgumentException("허용되지 않는 파일 확장자입니다.");
        }

        // 파일 크기 검증
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("파일 크기가 너무 큽니다.");
        }
    }

    private boolean isExtensionAllowed(String extension, Iterable<String> allowedExtensions) {
        for (String allowedExtension : allowedExtensions) {
            if (allowedExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    private String getExtension (String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            throw new IllegalArgumentException("파일 확장자가 존재하지 않습니다.");
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }
}
