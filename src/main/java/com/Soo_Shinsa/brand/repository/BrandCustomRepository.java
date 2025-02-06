package com.Soo_Shinsa.brand.repository;

import com.Soo_Shinsa.brand.dto.FindBrandAllResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandCustomRepository {
    Page<FindBrandAllResponseDto> getAllBrand(Pageable pageable);
}
