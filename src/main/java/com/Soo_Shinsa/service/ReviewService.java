package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.review.ReviewRequestDto;
import com.Soo_Shinsa.dto.review.ReviewResponseDto;
import com.Soo_Shinsa.dto.review.ReviewUpdateDto;
import com.Soo_Shinsa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponseDto createReview (Long orderItemId, ReviewRequestDto requestDto, User user);

    ReviewResponseDto getReview(Long reviewId);

    ReviewUpdateDto updateReview(Long reviewId, ReviewUpdateDto updateDto, User user);

    void delete(Long reviewId, User user);

    Page<ReviewResponseDto> getAllReviewProduct(Long productId, Pageable pageable);


}