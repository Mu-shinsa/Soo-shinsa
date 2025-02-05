package com.Soo_Shinsa.category.dto;

import com.Soo_Shinsa.category.model.Category;
import lombok.Builder;
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


    @Builder
    public FindCategoryResponseDto(Long id, Long brandId, Long parent, String name, List<FindCategoryResponseDto> children) {
        this.id = id;
        this.brandId = brandId;
        this.parent = parent;
        this.name = name;
        this.children = children;
    }

    public static FindCategoryResponseDto of(Category category) {
        return FindCategoryResponseDto.builder()
                .id(category.getId())
                .brandId(category.getBrand().getId())
                .name(category.getName())
                .parent(category.getParent().getId())
                .children(category.getChildren().stream().map(FindCategoryResponseDto::of).collect(Collectors.toList()))
                .build();
    }
}
