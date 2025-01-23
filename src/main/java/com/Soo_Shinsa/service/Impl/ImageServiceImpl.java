package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.model.Image;
import com.Soo_Shinsa.repository.ImageRepository;
import com.Soo_Shinsa.service.ImageService;
import com.Soo_Shinsa.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    public Image uploadImage(MultipartFile file, String dirName) {
        try {
            // S3에 파일 업로드
            String imageUrl = s3Uploader.upload(file, dirName);

            // 비즈니스 로직: 데이터베이스에 이미지 정보 저장
            Image image = new Image(file.getOriginalFilename(), imageUrl);
            return imageRepository.save(image);

        } catch (IOException e) {
            // 업로드 중 예외 처리
            log.error("이미지 업로드 실패: {}", e.getMessage());

            // 적절한 사용자 정의 예외 던지기
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("이미지가 존재하지 않습니다."));

        // S3에서 파일 삭제
        s3Uploader.deleteFile(image.getPath());

        // 데이터베이스에서 정보 삭제
        imageRepository.delete(image);
    }
}
