package com.Soo_Shinsa.category.dto;

import com.Soo_Shinsa.category.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private Long brand;
    private Category parent;
    private String name;

    public CategoryResponseDto(Long id, Long brand,Category parent, String name) {
        this.id = id;
        this.brand = brand;
        this.parent = parent;
        this.name = name;
    }

    public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getBrand().getId(),
                category.getParent(),
                category.getName()
        );
    }
}
