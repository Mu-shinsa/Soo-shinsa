package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.review.ReviewRequestDto;
import com.Soo_Shinsa.dto.review.ReviewResponseDto;
import com.Soo_Shinsa.dto.review.ReviewUpdateDto;

public interface ReviewService {
    ReviewResponseDto createReview (Long orderItemId, ReviewRequestDto requestDto);
    ReviewResponseDto getReview(Long reviewId);
    ReviewUpdateDto updateReview(Long reviewId, ReviewUpdateDto updateDto);
    void delete(Long reviewId);
}
