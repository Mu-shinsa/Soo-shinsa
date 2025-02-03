package com.Soo_Shinsa.review;

import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_REVIEW;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE r.product.id = :productId " +
            "ORDER BY r.createdAt DESC")
    Page<Review> findAllByProductId(@Param("productId") Long productId, Pageable pageable);

    default Review findByIdOrElseThrow(Long reviewId) {
        return findById(reviewId).orElseThrow(() -> new NotFoundException(NOT_FOUND_REVIEW));
    }
}
