package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
