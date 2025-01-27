package com.Soo_Shinsa.category;


import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.user.model.User;

public interface CategoryService {
    CategoryResponseDto create(Long brandId, User user, CategoryRequestDto categoryRequestDto);

}