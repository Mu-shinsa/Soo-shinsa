package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.entity.Brand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BrandResponseDto {

    private Long id;
    private String registrationNum;
    private String name;
    private String context;

    public BrandResponseDto(Long id, String registrationNum, String name, String context) {
        this.id = id;
        this.registrationNum = registrationNum;
        this.name = name;
        this.context = context;
    }

    public static BrandResponseDto toDto(Brand brand) {
        return new BrandResponseDto(
                brand.getId(),
                brand.getRegistrationNum(),
                brand.getName(),
                brand.getContext());
    }
}
