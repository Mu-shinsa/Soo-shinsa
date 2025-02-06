package com.Soo_Shinsa.category.dto;

import com.Soo_Shinsa.category.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindCategoryResponseDto {

    private Long id;

    private Long brandId;

    private Long parent;

    private String name;

    private Long totalCount;

    @Builder
    public FindCategoryResponseDto(Long id, Long brandId, Long parent, String name, Long totalCount) {
        this.id = id;
        this.brandId = brandId;
        this.parent = parent;
        this.name = name;
        this.totalCount = totalCount;
    }

    public static FindCategoryResponseDto of(Category category) {
        return FindCategoryResponseDto.builder()
                .id(category.getId())
                .brandId(category.getBrand().getId())
                .name(category.getName())
                .parent(category.getParent().getId())
                .build();
    }
}
