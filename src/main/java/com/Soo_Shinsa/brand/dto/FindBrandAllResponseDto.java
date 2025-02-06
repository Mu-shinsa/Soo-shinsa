package com.Soo_Shinsa.brand.dto;

import com.Soo_Shinsa.brand.model.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindBrandAllResponseDto {
    private Long id;
    private String name;

    @Builder
    public FindBrandAllResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static FindBrandAllResponseDto of(Brand brand) {
        return FindBrandAllResponseDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
