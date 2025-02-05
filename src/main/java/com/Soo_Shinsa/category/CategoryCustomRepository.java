package com.Soo_Shinsa.category;

import com.Soo_Shinsa.category.dto.FindCategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryCustomRepository {
    Page<FindCategoryResponseDto> findAllCategories(Pageable pageable);
    Page<FindCategoryResponseDto> findByBrandId(Long brandId, Pageable pageable);
}
