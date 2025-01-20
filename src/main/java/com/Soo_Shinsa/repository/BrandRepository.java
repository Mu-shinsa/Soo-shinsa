package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository  extends JpaRepository<Brand, Long> {
    Optional<Brand> findById(Long id);

    List<Brand> findAllByUserUserId(Long userId);
}
