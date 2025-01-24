package com.Soo_Shinsa.image;

import com.Soo_Shinsa.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    @Override
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

    @Transactional
    @Override
    public void deleteImage(String imageUrl) {
        if (imageUrl != null) {
            s3Uploader.deleteFile(imageUrl);
        }
    }

    @Transactional
    @Override
    public Image updateImage(MultipartFile newFile, String oldImageUrl, String dirName) {
        try {
            String newImageUrl = s3Uploader.updateFile(newFile, oldImageUrl, dirName);
            Image image = new Image(newFile.getOriginalFilename(), newImageUrl);
            return imageRepository.save(image);
        } catch (IOException e) {
            log.error("이미지 업데이트 실패: {}", e.getMessage());
            throw new RuntimeException("이미지 업데이트 중 오류가 발생했습니다.");
        }
    }
}
