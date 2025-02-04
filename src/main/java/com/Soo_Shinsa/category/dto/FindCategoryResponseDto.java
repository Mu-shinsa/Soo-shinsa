package com.Soo_Shinsa.category.dto;

import com.Soo_Shinsa.category.model.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class FindCategoryResponseDto {

    private Long id;

    private Long brandId;

    private Long parent;

    private String name;

    private List<FindCategoryResponseDto> children = new ArrayList<>();


    public static FindCategoryResponseDto of(Category category) {
        return new FindCategoryResponseDto(
                category.getId(),
                category.getBrand().getId(),
                category.getParent() != null ? category.getParent().getId() : null,
                category.getName(),
                category.getChildren()
                        .stream()
                        .map(FindCategoryResponseDto::of)
                        .collect(Collectors.toList())
        );
    }
    public FindCategoryResponseDto(Long id, Long brandId, Long parent, String name, List<FindCategoryResponseDto> children ) {
        this.id = id;
        this.brandId = brandId;
        this.parent = parent;
        this.name = name;
        this.children = children;
    }
}
