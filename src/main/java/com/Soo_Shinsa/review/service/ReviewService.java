package com.Soo_Shinsa.review.service;

import com.Soo_Shinsa.review.dto.ReviewRateDto;
import com.Soo_Shinsa.review.dto.ReviewRequestDto;
import com.Soo_Shinsa.review.dto.ReviewResponseDto;
import com.Soo_Shinsa.review.dto.ReviewUpdateDto;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
    ReviewResponseDto createReview(Long orderItemId, ReviewRequestDto requestDto, User user, MultipartFile imageFile);

    ReviewResponseDto getReview(Long reviewId);

    ReviewUpdateDto updateReview(Long reviewId, ReviewUpdateDto updateDto, User user, MultipartFile imageFile);

    void delete(Long reviewId, User user);

    Page<ReviewResponseDto> getReviewsByProductId(Long productId, ReviewRateDto reviewRateDto, int page, int size);
}