package com.Soo_Shinsa.category.repository;

import com.Soo_Shinsa.category.dto.FindCategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryCustomRepository {
    Page<FindCategoryResponseDto> findAllCategories(Pageable pageable);
}
