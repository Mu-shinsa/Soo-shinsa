package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.ReviewRequestDto;
import com.Soo_Shinsa.dto.ReviewResponseDto;
import com.Soo_Shinsa.dto.ReviewUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponseDto createReview(Long orderItemId, ReviewRequestDto requestDto);

    ReviewResponseDto getReview(Long reviewId);

    ReviewUpdateDto updateReview(Long reviewId, ReviewUpdateDto updateDto);

    void delete(Long reviewId);

    Page<ReviewResponseDto> getAllReviewProduct(Long productId, Pageable pageable);


}