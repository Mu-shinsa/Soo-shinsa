package com.Soo_Shinsa.category.dto;

import com.Soo_Shinsa.brand.Brand;
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

    private Brand brand;

    private Long parent;

    private String name;

    private List<FindCategoryResponseDto> children = new ArrayList<>();


    public static FindCategoryResponseDto of(Category category) {
        return new FindCategoryResponseDto(
                category.getId(),
                category.getBrand(),
                category.getParent() != null ? category.getParent().getId() : null,
                category.getName(),
                category.getChildren()
                        .stream()
                        .map(FindCategoryResponseDto::of)
                        .collect(Collectors.toList())
        );
    }
    public FindCategoryResponseDto(Long id, Brand brand, Long parent, String name, List<FindCategoryResponseDto> children ) {
        this.id = id;
        this.brand = brand;
        this.parent = parent;
        this.name = name;
        this.children = children;
    }
}
