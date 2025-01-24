package com.Soo_Shinsa.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface BrandRepository  extends JpaRepository<Brand, Long> {
    Optional<Brand> findById(Long id);

    List<Brand> findAllByUserUserId(Long userId);

    default Brand findByIdOrElseThrow(Long brandId) {
        return findById(brandId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 브랜드를 찾을 수 없습니다.")
        );
    }
}
