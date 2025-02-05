package com.Soo_Shinsa.review;

import com.Soo_Shinsa.review.dto.ReviewRateDto;
import com.Soo_Shinsa.review.dto.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewResponseDto> getReviewsAllByProductId(Long productId, ReviewRateDto reviewRateDto, Pageable pageable);
}
