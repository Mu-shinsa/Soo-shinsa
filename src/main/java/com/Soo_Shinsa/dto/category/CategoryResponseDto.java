package com.Soo_Shinsa.dto.category;

import com.Soo_Shinsa.model.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private Category parent;
    private String name;

    public CategoryResponseDto(Long id, Category parent, String name) {
       this.id = id;
       this.parent = parent;
       this.name = name;
    }

    public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getParent(),
                category.getName()
        );
    }
}
