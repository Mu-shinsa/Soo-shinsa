package com.Soo_Shinsa.category.dto;

import com.Soo_Shinsa.category.model.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private Long brand;
    private CategoryResponseDto parent;
    private String name;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.brand = category.getBrand().getId();
        this.parent = (category.getParent() != null) ? new CategoryResponseDto(category.getParent()) : null;
        this.name = category.getName();
    }

    public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(category);
    }
}
