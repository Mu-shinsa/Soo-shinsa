package com.Soo_Shinsa.category;

import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.category.dto.CategoryUpdateRequestDto;
import com.Soo_Shinsa.category.dto.FindCategoryResponseDto;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponseDto create(Long brandId, User user, CategoryRequestDto categoryRequestDto);

    CategoryResponseDto findById(Long categoryId);

    Page<FindCategoryResponseDto> findByBrandId(Long brandId, int page, int size);

    Page<FindCategoryResponseDto> findAll(int page, int size);

    CategoryResponseDto update(User user, CategoryUpdateRequestDto dto, Long categoryId);
}