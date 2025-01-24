package com.Soo_Shinsa.review;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByProductId(Long productId, Pageable pageable);
}
