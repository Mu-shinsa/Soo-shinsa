package com.Soo_Shinsa.review;

import com.Soo_Shinsa.review.model.Review;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE r.product.id = :productId " +
            "ORDER BY r.createdAt DESC")
    Page<Review> findAllByProductId(@Param("productId") Long productId, Pageable pageable);

    default Review findById(Long reviewId, String exceptionMessage) {
        return findById(reviewId).orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }
}
