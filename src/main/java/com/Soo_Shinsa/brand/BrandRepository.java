package com.Soo_Shinsa.brand;

import com.Soo_Shinsa.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_BRAND;

public interface BrandRepository extends JpaRepository<Brand, Long>, BrandCustomRepository {

    List<Brand> findAllByUserUserId(Long userId);

    default Brand findByIdOrElseThrow(Long brandId) {
        return findById(brandId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_BRAND));
    }
}
