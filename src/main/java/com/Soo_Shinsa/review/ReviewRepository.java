package com.Soo_Shinsa.review;

import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_REVIEW;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review findByIdOrElseThrow(Long reviewId) {
        return findById(reviewId).orElseThrow(() -> new NotFoundException(NOT_FOUND_REVIEW));
    }
}
