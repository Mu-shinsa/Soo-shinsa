package com.Soo_Shinsa.brand;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository  extends JpaRepository<Brand, Long> {
    Optional<Brand> findById(Long id);

    List<Brand> findAllByUserUserId(Long userId);
}
